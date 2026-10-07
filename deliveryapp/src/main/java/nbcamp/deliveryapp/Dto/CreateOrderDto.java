package nbcamp.deliveryapp.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateOrderDto {

    @Min(value = 1, message = "메뉴아이디는 1이상이어야 합니다.")
    private Long menuId;

    @Min(value = 1, message = "수량은 1이상이어야 합니다.")
    private int count;

    @NotNull @Size(min = 1, message = "주소를 채워야 합니다.")
    private String address;
}
