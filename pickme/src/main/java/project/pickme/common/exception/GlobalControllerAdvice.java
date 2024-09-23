package project.pickme.common.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404() {
		return "/error/404";
	}

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handle500() {
		return "error/500";
	}
}
