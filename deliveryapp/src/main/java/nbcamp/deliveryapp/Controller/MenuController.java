package nbcamp.deliveryapp.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbcamp.deliveryapp.Dto.MenuDto;
import nbcamp.deliveryapp.Enum.UserRole;
import nbcamp.deliveryapp.Service.AuthService;
import nbcamp.deliveryapp.Service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> addMenu(@Valid @RequestBody MenuDto menuDto, HttpServletRequest request) {
        try {
            authService.validRoleCheck(request, UserRole.OWNER);
            menuService.save(menuDto);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
