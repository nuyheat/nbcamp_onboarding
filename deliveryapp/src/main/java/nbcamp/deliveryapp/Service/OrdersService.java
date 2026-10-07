package nbcamp.deliveryapp.Service;

import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Dto.CreateOrderDto;
import nbcamp.deliveryapp.Dto.OrderResponseDto;
import nbcamp.deliveryapp.Entity.Menu;
import nbcamp.deliveryapp.Entity.Orders;
import nbcamp.deliveryapp.Entity.Users;
import nbcamp.deliveryapp.Enum.OrderStatus;
import nbcamp.deliveryapp.Exception.CustomException;
import nbcamp.deliveryapp.Exception.ErrorCode;
import nbcamp.deliveryapp.Repository.MenuRepository;
import nbcamp.deliveryapp.Repository.OrdersRepository;
import nbcamp.deliveryapp.Repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final MenuRepository menuRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public void create(CreateOrderDto orderDto, String loginId) {
        Menu menu = menuRepository.findById(orderDto.getMenuId())
                .orElseThrow(() -> new CustomException(ErrorCode.C404_MENU_NOT_FOUND));

        Users user = usersRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.C404_USER_NOT_FOUND));

        Orders order = new Orders();

        order.setUser(user);
        order.setMenu(menu);
        order.setTotalPrice(menu.getPrice() * orderDto.getCount());
        order.setAddress(orderDto.getAddress());
        order.setCount(orderDto.getCount());
        order.setStatus(OrderStatus.ORDERED);

        ordersRepository.save(order);
    }

    public List<OrderResponseDto> findAllByLoginId(String loginId) {
        return ordersRepository
                .findAllByUserLoginIdOrMenuUserLoginId(loginId, loginId)
                .stream()
                .map(OrderResponseDto::new)
                .toList();

    }

    @Transactional
    public void updateOrderStatusToCanceled(Long orderId, String loginId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.C404_MENU_NOT_FOUND));

        if (!order.getUser().getLoginId().equals(loginId)) {
            throw new CustomException(ErrorCode.C403_NO_AUTHORIZATION);
        }

        if (!order.getStatus().equals(OrderStatus.ORDERED)) {
            throw new CustomException(ErrorCode.C400_BAD_REQUEST);
        }

        order.setStatus(OrderStatus.CANCELED);
    }
}
