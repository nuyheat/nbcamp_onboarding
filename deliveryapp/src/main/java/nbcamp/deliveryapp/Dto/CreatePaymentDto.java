package nbcamp.deliveryapp.Dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import nbcamp.deliveryapp.Enum.PaymentType;

@Getter
public class CreatePaymentDto {

    @Min(value = 1, message = "아이디는 1이상이어야 합니다.")
    private Long orderId;

    private PaymentType paymentType;
}
