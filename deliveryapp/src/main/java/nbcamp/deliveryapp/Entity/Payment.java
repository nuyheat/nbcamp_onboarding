package nbcamp.deliveryapp.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nbcamp.deliveryapp.Enum.PaymentStatus;
import nbcamp.deliveryapp.Enum.PaymentType;

@Entity
@Getter @Setter
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Orders order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Embedded
    private BaseTime baseTime;
}
