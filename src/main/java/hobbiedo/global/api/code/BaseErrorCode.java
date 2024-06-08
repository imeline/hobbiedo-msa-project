package hobbiedo.global.api.code;

import hobbiedo.global.api.dto.ErrorReasonDto;

public interface BaseErrorCode {
	ErrorReasonDto getReason();

	ErrorReasonDto getReasonHttpStatus();
}
