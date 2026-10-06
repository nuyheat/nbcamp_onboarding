package nbcamp.deliveryapp.Service;

import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Dto.AddMenuDto;
import nbcamp.deliveryapp.Dto.MenuResponseDto;
import nbcamp.deliveryapp.Dto.UpdateMenuDto;
import nbcamp.deliveryapp.Entity.BaseTime;
import nbcamp.deliveryapp.Entity.Menu;
import nbcamp.deliveryapp.Entity.Users;
import nbcamp.deliveryapp.Exception.CustomException;
import nbcamp.deliveryapp.Exception.ErrorCode;
import nbcamp.deliveryapp.Repository.MenuRepository;
import nbcamp.deliveryapp.Repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final UsersRepository usersRepository;
    private final AuthService authService;

    @Transactional
    public void save(AddMenuDto menuDto, String loginId) {
        //탐색
        Users user = usersRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.C404_USER_NOT_FOUND));

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

    @Transactional
    public void update(UpdateMenuDto menuDto, String loginId) {
        //탐색
        Menu menu = menuRepository.findByIdAndIsDeleted(menuDto.getId(), false)
                .orElseThrow(() -> new CustomException(ErrorCode.C404_MENU_NOT_FOUND));

        //검증
        if (!menu.getUser().getLoginId().equals(loginId)) {
            throw new CustomException(ErrorCode.C403_NO_AUTHORIZATION);
        }

        //저장
        BaseTime baseTime = new BaseTime(menu.getBaseTime().getCreatedAt(), LocalDateTime.now());

        menu.setName(menuDto.getName());
        menu.setPrice(menuDto.getPrice());
        menu.setDescription(menuDto.getDescription());
        menu.setBaseTime(baseTime);
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
