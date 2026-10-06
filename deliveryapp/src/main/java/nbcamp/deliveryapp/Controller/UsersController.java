package nbcamp.deliveryapp.Controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbcamp.deliveryapp.Config.JwtUtil;
import nbcamp.deliveryapp.Dto.LoginDto;
import nbcamp.deliveryapp.Dto.UserDto;
import nbcamp.deliveryapp.Exception.CustomException;
import nbcamp.deliveryapp.Exception.ErrorCode;
import nbcamp.deliveryapp.Service.AuthService;
import nbcamp.deliveryapp.Service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserDto userDto,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.C400_INVALID_INPUT_VALUE);
        }
        usersService.join(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.C400_INVALID_INPUT_VALUE);
        }
        String token = authService.login(loginDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(JwtUtil.AUTHORIZATION_HEADER, token)
                .build();
    }
}
