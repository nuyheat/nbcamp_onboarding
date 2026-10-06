package nbcamp.deliveryapp.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
public class BaseTime {

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    public BaseTime(LocalDateTime modifiedAt, LocalDateTime createdAt) {
        this.modifiedAt = modifiedAt;
        this.createdAt = createdAt;
    }

    public BaseTime() {
        this.modifiedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
