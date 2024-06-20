package hobbiedo.global.api.code;

import hobbiedo.global.api.dto.ReasonDto;

public interface BaseCode {
	ReasonDto getReason();

	ReasonDto getReasonHttpStatus();
}
