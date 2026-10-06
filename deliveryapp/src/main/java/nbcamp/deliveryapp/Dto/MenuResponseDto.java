package nbcamp.deliveryapp.Dto;

import lombok.Getter;
import nbcamp.deliveryapp.Entity.Menu;

@Getter
public class MenuResponseDto {
    private final Long id;
    private final String name;
    private final int price;
    private final String description;

    public  MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
    }
}
