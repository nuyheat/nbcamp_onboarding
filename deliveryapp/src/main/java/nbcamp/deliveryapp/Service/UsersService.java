package nbcamp.deliveryapp.Service;

import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Dto.LoginDto;
import nbcamp.deliveryapp.Dto.UserDto;
import nbcamp.deliveryapp.Entity.Users;
import nbcamp.deliveryapp.Exception.CustomException;
import nbcamp.deliveryapp.Exception.ErrorCode;
import nbcamp.deliveryapp.Repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(UserDto userDto) {
        //중복검증
        if (usersRepository.existsByLoginId(userDto.getLoginId())) {
            throw new CustomException(ErrorCode.C409_DUPLICATE_USER);
        }

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userDto.getLoginPassword());

        //저장
        Users user = new Users();
        user.setLoginId(userDto.getLoginId());
        user.setLoginPassword(encodedPassword);
        user.setRole(userDto.getRole());

        usersRepository.save(user);
    }

    public void login(LoginDto loginDto) {
        //아이디 검색
        Users findUser = usersRepository.findByLoginId(loginDto.getLoginId())
                .orElseThrow(IllegalArgumentException::new);

        //비밀번호 검증
        String encodedPassword = findUser.getLoginPassword();
        String rawPassword = loginDto.getLoginPassword();
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IllegalArgumentException();
        }
    }

}