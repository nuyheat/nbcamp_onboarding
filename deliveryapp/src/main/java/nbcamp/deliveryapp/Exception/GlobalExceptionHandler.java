package nbcamp.deliveryapp.Exception; // 프로젝트 패키지 경로에 맞게 지정

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //모든 비즈니스 예외를 하나로 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();

        Map<String, Object> response = new HashMap<>();
        response.put("status", errorCode.getStatus().value());
        response.put("message", e.getMessage()); //덮어쓴 메시지가 있으면 그 메시지가 출력됨

        return ResponseEntity
                .status(errorCode.getStatus()) //여기서 상태코드가 유연하게 결정
                .body(response);
    }
}