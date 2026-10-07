package nbcamp.deliveryapp.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Dto.CreateMenuDto;
import nbcamp.deliveryapp.Dto.MenuResponseDto;
import nbcamp.deliveryapp.Dto.UpdateMenuDto;
import nbcamp.deliveryapp.Enum.UserRole;
import nbcamp.deliveryapp.Exception.CustomException;
import nbcamp.deliveryapp.Exception.ErrorCode;
import nbcamp.deliveryapp.Service.AuthService;
import nbcamp.deliveryapp.Service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> createMenu(@Valid @RequestBody CreateMenuDto menuDto,
                                             BindingResult bindingResult,
                                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.C400_INVALID_INPUT_VALUE);
        }

        String loginId = authService.getLoginIdFromRequest(request);

        authService.validRoleCheck(request, UserRole.OWNER);
        menuService.create(menuDto, loginId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDto>> getAllMenuByIsDeleted(@RequestParam(required = true, name = "isDeleted") boolean isDeleted) {
        List<MenuResponseDto> menuList = menuService.findAllByIsDeleted(isDeleted);

        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(menuList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDto> getMenuById(@PathVariable Long id) {
        MenuResponseDto menuResponseDto = menuService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuResponseDto);
    }

    @PatchMapping
    public ResponseEntity<String> updateMenu(@Valid @RequestBody UpdateMenuDto menuDto,
                                             BindingResult bindingResult,
                                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.C400_INVALID_INPUT_VALUE);
        }

        String loginId = authService.getLoginIdFromRequest(request);

        authService.validRoleCheck(request, UserRole.OWNER);
        menuService.update(menuDto, loginId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id,
                                             HttpServletRequest request) {
        String loginId = authService.getLoginIdFromRequest(request);

        authService.validRoleCheck(request, UserRole.OWNER);
        menuService.updateIsDeletedToTrue(id, loginId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
