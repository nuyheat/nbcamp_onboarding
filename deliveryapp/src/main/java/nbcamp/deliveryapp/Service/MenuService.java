package nbcamp.deliveryapp.Service;

import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Dto.CreateMenuDto;
import nbcamp.deliveryapp.Dto.MenuResponseDto;
import nbcamp.deliveryapp.Dto.UpdateMenuDto;
import nbcamp.deliveryapp.Entity.Menu;
import nbcamp.deliveryapp.Entity.Users;
import nbcamp.deliveryapp.Exception.CustomException;
import nbcamp.deliveryapp.Exception.ErrorCode;
import nbcamp.deliveryapp.Repository.MenuRepository;
import nbcamp.deliveryapp.Repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final UsersRepository usersRepository;
    private final AuthService authService;

    @Transactional
    public void create(CreateMenuDto menuDto, String loginId) {
        Users user = usersRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.C404_USER_NOT_FOUND));

        Menu menu = new Menu();

        menu.setName(menuDto.getName());
        menu.setPrice(menuDto.getPrice());
        menu.setDescription(menuDto.getDescription());
        menu.setUser(user);

        menuRepository.save(menu);
    }

    @Transactional
    public void update(UpdateMenuDto menuDto, String loginId) {
        Menu menu = menuRepository.findByIdAndIsDeleted(menuDto.getId(), false)
                .orElseThrow(() -> new CustomException(ErrorCode.C404_MENU_NOT_FOUND));

        if (!menu.getUser().getLoginId().equals(loginId)) {
            throw new CustomException(ErrorCode.C403_NO_AUTHORIZATION);
        }


        menu.setName(menuDto.getName());
        menu.setPrice(menuDto.getPrice());
        menu.setDescription(menuDto.getDescription());
    }

    @Transactional
    public void updateIsDeletedToTrue(Long menuId, String loginId) {
        Menu menu = menuRepository.findByIdAndIsDeleted(menuId, false)
                .orElseThrow(() -> new CustomException(ErrorCode.C404_MENU_NOT_FOUND));

        if (!menu.getUser().getLoginId().equals(loginId)) {
            throw new CustomException(ErrorCode.C403_NO_AUTHORIZATION);
        }

        menu.setDeleted(true);
    }

    public MenuResponseDto findById(Long id) {
        Menu menu = menuRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new CustomException(ErrorCode.C404_MENU_NOT_FOUND));

        return new MenuResponseDto(menu);
    }

    public List<MenuResponseDto> findAllByIsDeleted(boolean isDeleted) {
        return menuRepository.findAllByIsDeleted(isDeleted)
                .stream()
                .map(MenuResponseDto::new)
                .toList();
    }
}
