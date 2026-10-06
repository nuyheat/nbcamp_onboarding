package nbcamp.deliveryapp.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateMenuDto {

    @Min(value = 1, message = "아이디는 1이상 이어야 합니다.")
    private Long id;

    @NotNull
    @Size(min = 1, message = "이름을 채워야 합니다.")
    private String name;

    @Min(value = 1, message = "가격은 1이상 이어야 합니다.")
    private int price;

    @NotNull
    private String description;
}
