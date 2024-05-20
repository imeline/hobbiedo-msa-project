package hobbiedo.gateway.global.code;

import hobbiedo.gateway.global.dto.ReasonDto;

public interface BaseCode {
    ReasonDto getReason();

    ReasonDto getReasonHttpStatus();
}
