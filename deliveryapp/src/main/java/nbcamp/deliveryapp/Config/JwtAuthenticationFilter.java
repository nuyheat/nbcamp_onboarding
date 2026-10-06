package nbcamp.deliveryapp.Config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbcamp.deliveryapp.Config.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 요청 헤더에서 토큰 추출
        String tokenValue = jwtUtil.getJwtFromHeader(request);

        if (StringUtils.hasText(tokenValue)) {
            // 2. 토큰 유효성 검증
            if (jwtUtil.validateToken(tokenValue)) {
                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

                // 3. 인증 처리 (SecurityContext에 인증 유저 정보 채우기)
                setAuthentication(info.getSubject(), (String) info.get("role"));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    // SecurityContext에 인증 정보 생성 및 등록
    public void setAuthentication(String loginId, String role) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // 유저 권한 부여 (예: ROLE_CUSTOMER, ROLE_OWNER 등)
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginId, null, List.of(authority));

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}