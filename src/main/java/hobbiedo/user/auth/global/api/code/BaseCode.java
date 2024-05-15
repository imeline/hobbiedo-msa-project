package hobbiedo.user.auth.global.api.code;

import hobbiedo.user.auth.global.api.dto.ReasonDto;

public interface BaseCode {
	ReasonDto getReason();

	ReasonDto getReasonHttpStatus();
}
