package nbcamp.deliveryapp.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Dto.CreateOrderDto;
import nbcamp.deliveryapp.Enum.UserRole;
import nbcamp.deliveryapp.Exception.CustomException;
import nbcamp.deliveryapp.Exception.ErrorCode;
import nbcamp.deliveryapp.Service.AuthService;
import nbcamp.deliveryapp.Service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final AuthService authService;
    private final OrdersService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@Valid @RequestBody CreateOrderDto orderDto,
                                              BindingResult bindingResult,
                                              HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.C400_INVALID_INPUT_VALUE);
        }

        String loginId = authService.getLoginIdFromRequest(request);

        authService.validRoleCheck(request, UserRole.CUSTOMER);
        orderService.create(orderDto, loginId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
