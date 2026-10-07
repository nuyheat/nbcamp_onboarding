package nbcamp.deliveryapp.Service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Config.JwtUtil;
import nbcamp.deliveryapp.Dto.LoginDto;
import nbcamp.deliveryapp.Entity.Users;
import nbcamp.deliveryapp.Enum.UserRole;
import nbcamp.deliveryapp.Exception.CustomException;
import nbcamp.deliveryapp.Exception.ErrorCode;
import nbcamp.deliveryapp.Repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(LoginDto loginDto) {
        // 1. loginId 존재 여부 확인
        Users user = userRepository.findByLoginId(loginDto.getLoginId())
                .orElseThrow(() -> new CustomException(ErrorCode.C401_UNAUTHORIZE_USER));

        // 2. 비밀번호 암호화 비교 검증
        if (!passwordEncoder.matches(loginDto.getLoginPassword(), user.getLoginPassword())) {
            throw new CustomException(ErrorCode.C401_UNAUTHORIZE_USER);
        }

        // 3. 인증 성공 시 JWT 토큰 생성 및 반환
        return jwtUtil.createToken(user.getLoginId(), user.getRole());
    }

    public void validRoleCheck(HttpServletRequest request, UserRole needRole) {
        String token= jwtUtil.getJwtFromHeader(request);
        Claims info = jwtUtil.getUserInfoFromToken(token);
        String userRole = info.get("role", String.class);

        if (!userRole.equals(needRole.name())) {
            throw new CustomException(ErrorCode.C403_NO_AUTHORIZATION);
        }
    }

    public String getLoginIdFromRequest(HttpServletRequest request) {
        String token= jwtUtil.getJwtFromHeader(request);
        Claims info = jwtUtil.getUserInfoFromToken(token);

        return  info.getSubject();
    }
}
