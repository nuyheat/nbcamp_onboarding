package nbcamp.deliveryapp.Dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import nbcamp.deliveryapp.Enum.UserRole;

@Getter
public class UserDto {

    @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하이어야 합니다.")
    private String loginId;

    @Size(min = 8, message = "비밀번호는 8자 이상 20자 이하이어야 합니다.")
    private String loginPassword;
    private UserRole role;
}
