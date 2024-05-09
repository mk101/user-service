package kolesov.maksim.mapping.user.config;

import kolesov.maksim.mapping.user.dto.ResponseDto;
import kolesov.maksim.mapping.user.exception.ForbiddenException;
import kolesov.maksim.mapping.user.exception.NotFoundException;
import kolesov.maksim.mapping.user.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseDto<Void>> handleForbiddenException(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto<>(null, false, e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto<Void>> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto<>(null, false, e.getMessage()));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ResponseDto<Void>> handleServiceException(ServiceException e) {
        log.error("Bad request: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(null, false, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> handleException(Exception e) {
        log.error("Internal server error: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto<>(null, false, "Internal server error"));
    }

}
