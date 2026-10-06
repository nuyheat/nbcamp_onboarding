package nbcamp.deliveryapp.Service;

import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Dto.MenuDto;
import nbcamp.deliveryapp.Entity.Menu;
import nbcamp.deliveryapp.Repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final AuthService authService;

    public void save(MenuDto menuDto) {
        //저장
        Menu menu = new Menu();
        menu.setName(menuDto.getName());
        menu.setPrice(menuDto.getPrice());
        menu.setDescription(menuDto.getDescription());
        menuRepository.save(menu);
    }

    public Optional<Menu> findById(Long id) {
        return menuRepository.findById(id);
    }

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
