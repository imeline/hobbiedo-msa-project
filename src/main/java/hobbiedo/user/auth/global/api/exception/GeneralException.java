package hobbiedo.user.auth.global.api.exception;

import hobbiedo.user.auth.global.api.code.BaseErrorCode;
import hobbiedo.user.auth.global.api.dto.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
	private BaseErrorCode errorCode;

	public ErrorReasonDto getErrorReason() {
		return errorCode.getReason();
	}

	public ErrorReasonDto getErrorReasonHttpStatus() {
		return errorCode.getReasonHttpStatus();
	}
}
