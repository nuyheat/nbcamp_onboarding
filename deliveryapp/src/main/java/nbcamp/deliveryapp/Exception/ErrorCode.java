package nbcamp.deliveryapp.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //400 BAD REQUEST
    C400_INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),

    //401
    C401_UNAUTHORIZE_USER(HttpStatus.UNAUTHORIZED, "존재하지 않거나 잘못된 아이디/비밀번호 입니다."),

    //403 FORBIDDEN
    C403_NO_AUTHORIZATION(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    //404 NOT FOUND
    C404_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    C404_MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 메뉴입니다."),

    //409 CONFLICT
    C409_DUPLICATE_USER(HttpStatus.CONFLICT, "이미 존재하는 유저입니다.");

    private final HttpStatus status;
    private final String message;

}