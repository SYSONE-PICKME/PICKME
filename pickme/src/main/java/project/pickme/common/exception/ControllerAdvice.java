package project.pickme.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handlerBizException(BusinessException e){
		return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(e.getErrorCode().getMessage());
	}
}
