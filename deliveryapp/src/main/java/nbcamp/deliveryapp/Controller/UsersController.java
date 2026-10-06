package nbcamp.deliveryapp.Controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbcamp.deliveryapp.Config.JwtUtil;
import nbcamp.deliveryapp.Dto.LoginDto;
import nbcamp.deliveryapp.Dto.UserDto;
import nbcamp.deliveryapp.Service.AuthService;
import nbcamp.deliveryapp.Service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserDto userDto) {
        try {
            usersService.join(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        try {
            String token = authService.login(loginDto);

            // Header에 JWT 토큰 싣기 ("Authorization": "Bearer eyJhbG...")
            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
