package nbcamp.deliveryapp.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import nbcamp.deliveryapp.Enum.OrderStatus;

@Getter
public class UpdateOrderStatusDto {

    @Min(value = 1, message = "아이디는 1이상이어야 합니다.")
    private Long orderId;
    
    private OrderStatus orderStatus;
}
