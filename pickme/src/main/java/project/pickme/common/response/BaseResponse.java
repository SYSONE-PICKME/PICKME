package project.pickme.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"success", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BaseResponse<T>(Boolean success, T data) {

	public static <T> BaseResponse<T> ok(T data) {
		return new BaseResponse<>(true, data);
	}

	public static BaseResponse<Void> ok() {
		return new BaseResponse<>(true, null);
	}
}
