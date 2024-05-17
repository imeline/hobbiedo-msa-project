package hobbiedo.user.auth.global.api.code;

import hobbiedo.user.auth.global.api.dto.ErrorReasonDto;

public interface BaseErrorCode {
	ErrorReasonDto getReason();

	ErrorReasonDto getReasonHttpStatus();
}
