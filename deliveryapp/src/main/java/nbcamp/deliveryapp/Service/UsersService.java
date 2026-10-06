package nbcamp.deliveryapp.Service;

import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Dto.UserDto;
import nbcamp.deliveryapp.Entity.BaseTime;
import nbcamp.deliveryapp.Entity.Users;
import nbcamp.deliveryapp.Repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public void save(UserDto userDto) throws Exception {
        // 중복검증
        if (usersRepository.existsByLoginId(userDto.getLoginId())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        // 비밀번호 암호화
        PasswordEncoder bpe = new BCryptPasswordEncoder();
        String encodedPassword = bpe.encode(userDto.getLoginPassword());

        // 저장
        Users user = new Users();
        BaseTime baseTime = new BaseTime(LocalDateTime.now(), LocalDateTime.now());
        user.setLoginId(userDto.getLoginId());
        user.setLoginPassword(encodedPassword);
        user.setRole(userDto.getRole());
        user.setBaseTime(baseTime);

        usersRepository.save(user);
    }

//    public Users login(String loginId){
//        return usersRepository.findByLoginId(loginId).orElse(null);
//    }
}