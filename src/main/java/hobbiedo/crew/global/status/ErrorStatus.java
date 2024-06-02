package hobbiedo.crew.global.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {

	INTERNAL_SERVER_ERROR("SERVER500", "Internal server error"),
	BAD_REQUEST("REQUEST400", "Bad request"),

	NO_EXIST_IMAGE_CHAT("CHAT401", "조회 가능한 사진 체팅이 존재하지 않습니다.");

	private final String status;
	private final String message;

}
