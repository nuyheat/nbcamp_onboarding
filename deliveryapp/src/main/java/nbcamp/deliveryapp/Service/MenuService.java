package nbcamp.deliveryapp.Service;

import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Dto.MenuDto;
import nbcamp.deliveryapp.Dto.MenuResponseDto;
import nbcamp.deliveryapp.Entity.BaseTime;
import nbcamp.deliveryapp.Entity.Menu;
import nbcamp.deliveryapp.Entity.Users;
import nbcamp.deliveryapp.Repository.MenuRepository;
import nbcamp.deliveryapp.Repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final UsersRepository usersRepository;
    private final AuthService authService;

    @Transactional
    public void save(MenuDto menuDto, String loginId) throws NoSuchFieldException {
        //탐색
        Users user = usersRepository.findByLoginId(loginId)
                .orElseThrow(NoSuchFieldException::new);

        //저장
        Menu menu = new Menu();
        BaseTime baseTime = new BaseTime();

        menu.setName(menuDto.getName());
        menu.setPrice(menuDto.getPrice());
        menu.setDescription(menuDto.getDescription());
        menu.setBaseTime(baseTime);
        menu.setUser(user);

        menuRepository.save(menu);
    }

    public MenuResponseDto findById(Long id) {
        Menu menu = menuRepository.findByIdAndIsDeleted(id, false).orElseThrow(NoSuchElementException::new);

        return new MenuResponseDto(menu);
    }

    public List<MenuResponseDto> findAllByIsDeleted(boolean isDeleted) {
        return menuRepository.findAllByIsDeleted(isDeleted)
                .stream()
                .map(MenuResponseDto::new)
                .toList();
    }
}
