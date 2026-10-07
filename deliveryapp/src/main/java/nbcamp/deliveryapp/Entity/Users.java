package nbcamp.deliveryapp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import nbcamp.deliveryapp.Enum.UserRole;

@Entity
@Getter @Setter
public class Users extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하여야 합니다.")
    @Column(unique = true, nullable = false, length = 20)
    private String loginId;

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @Column(nullable = false)
    private String loginPassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

}
