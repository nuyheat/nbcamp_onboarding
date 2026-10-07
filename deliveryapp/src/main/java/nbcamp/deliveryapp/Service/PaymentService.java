package nbcamp.deliveryapp.Service;

import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Dto.CreatePaymentDto;
import nbcamp.deliveryapp.Entity.Orders;
import nbcamp.deliveryapp.Entity.Payment;
import nbcamp.deliveryapp.Enum.OrderStatus;
import nbcamp.deliveryapp.Enum.PaymentStatus;
import nbcamp.deliveryapp.Enum.PaymentType;
import nbcamp.deliveryapp.Exception.CustomException;
import nbcamp.deliveryapp.Exception.ErrorCode;
import nbcamp.deliveryapp.Repository.OrdersRepository;
import nbcamp.deliveryapp.Repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrdersRepository ordersRepository;

    @Transactional
    public void create(CreatePaymentDto paymentDto, String loginId) {
        Orders order = ordersRepository.findById(paymentDto.getOrderId())
                .orElseThrow(() -> new CustomException(ErrorCode.C404_ORDER_NOT_FOUND));

        if (!order.getUser().getLoginId().equals(loginId)) {
            throw new CustomException(ErrorCode.C403_NO_AUTHORIZATION);
        }

        if (!order.getStatus().equals(OrderStatus.ORDERED)) {
            throw new CustomException(ErrorCode.C403_NO_AUTHORIZATION);
        }

        Payment payment = new Payment();

        payment.setType(PaymentType.CARD);
        payment.setStatus(PaymentStatus.PAID);
        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        order.setStatus(OrderStatus.PAID);

        paymentRepository.save(payment);
    }
}
