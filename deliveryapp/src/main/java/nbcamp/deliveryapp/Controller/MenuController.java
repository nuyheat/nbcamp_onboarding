package nbcamp.deliveryapp.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbcamp.deliveryapp.Dto.MenuDto;
import nbcamp.deliveryapp.Dto.MenuResponseDto;
import nbcamp.deliveryapp.Enum.UserRole;
import nbcamp.deliveryapp.Service.AuthService;
import nbcamp.deliveryapp.Service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> addMenu(@Valid @RequestBody MenuDto menuDto, HttpServletRequest request) throws Exception {
        String userId = authService.getUserIdFromToken(request);

        authService.validRoleCheck(request, UserRole.OWNER);
        menuService.save(menuDto, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDto>> getAllMenuByIsDeleted(@RequestParam(required = true, name = "isDeleted") boolean isDeleted) {
        List<MenuResponseDto> menuList = menuService.findAllByIsDeleted(isDeleted);
        return  ResponseEntity.status(HttpStatus.OK).body(menuList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDto> getMenuById(@PathVariable Long id) {
        MenuResponseDto menuResponseDto = menuService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);
    }
}
