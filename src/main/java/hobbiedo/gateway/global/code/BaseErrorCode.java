package hobbiedo.gateway.global.code;

import hobbiedo.gateway.global.dto.ErrorReasonDto;

public interface BaseErrorCode {
    ErrorReasonDto getReason();

    ErrorReasonDto getReasonHttpStatus();
}
