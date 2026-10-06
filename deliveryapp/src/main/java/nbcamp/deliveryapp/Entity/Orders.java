package nbcamp.deliveryapp.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nbcamp.deliveryapp.Enum.OrderStatus;

@Entity
@Getter @Setter
public class Orders {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id",  nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private String Address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Embedded
    private BaseTime baseTime;
}
