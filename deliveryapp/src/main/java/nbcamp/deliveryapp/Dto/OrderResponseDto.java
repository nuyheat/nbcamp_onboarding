package nbcamp.deliveryapp.Dto;

import jakarta.persistence.*;
import lombok.Getter;
import nbcamp.deliveryapp.Entity.Menu;
import nbcamp.deliveryapp.Entity.Orders;
import nbcamp.deliveryapp.Entity.Users;
import nbcamp.deliveryapp.Enum.OrderStatus;

@Getter
public class OrderResponseDto {

    private final Long orderId;
    private final Long menuId;
    private final String menuName;
    private final int count;
    private final int totalPrice;
    private final String address;
    private final OrderStatus status;

    public OrderResponseDto(Orders order) {
        this.orderId = order.getId();
        this.menuId = order.getMenu().getId();
        this.menuName = order.getMenu().getName();
        this.count = order.getCount();
        this.totalPrice = order.getTotalPrice();
        this.address = order.getAddress();
        this.status = order.getStatus();
    }
}
