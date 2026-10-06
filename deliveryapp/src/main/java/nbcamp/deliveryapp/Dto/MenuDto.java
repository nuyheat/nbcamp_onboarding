package nbcamp.deliveryapp.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MenuDto {

    @NotNull @Size(min = 1, message = "이름을 채워야 합니다.")
    private String name;

    @Size(min = 1, message = "값은 1이상 이어야 합니다.")
    private int price;

    @NotNull
    private String description;

}
