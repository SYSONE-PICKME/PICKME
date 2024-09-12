package project.pickme.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
	protected ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode){
		this.errorCode = errorCode;
	}
}
