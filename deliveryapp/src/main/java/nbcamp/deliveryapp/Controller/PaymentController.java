package nbcamp.deliveryapp.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Dto.CreatePaymentDto;
import nbcamp.deliveryapp.Entity.Payment;
import nbcamp.deliveryapp.Enum.UserRole;
import nbcamp.deliveryapp.Service.AuthService;
import nbcamp.deliveryapp.Service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> createPayment(@Valid @RequestBody CreatePaymentDto paymentDto,
                                               BindingResult bindingResult,
                                                HttpServletRequest request) {
        authService.validRoleCheck(request, UserRole.CUSTOMER);
        String loginId = authService.getLoginIdFromRequest(request);
        paymentService.create(paymentDto, loginId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}

