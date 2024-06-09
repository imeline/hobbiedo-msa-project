package hobbiedo.global.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {

	INTERNAL_SERVER_ERROR("SERVER500", "Internal server error"),
	BAD_REQUEST("REQUEST400", "Bad request"),

	// CHAT 체팅
	NO_EXIST_IMAGE_CHAT("CHAT401", "조회 가능한 사진 체팅이 존재하지 않습니다."),
	NO_EXIST_CHAT("CHAT406", "조회 가능한 체팅이 존재하지 않습니다."),
	NO_EXIST_CHAT_UNREAD_STATUS("CHAT407", "조회 가능한 체팅 읽음 상태가 존재하지 않습니다."),
	NO_EXIST_UNREAD_COUNT("CHAT408", "조회 가능한 읽지 않은 체팅 개수가 존재하지 않습니다."),

	// REGION 활동지역
	NO_EXIST_MEMBER_REGION("REGION401", "활동 지역이 존재하지 않습니다."),
	NO_EXIST_BASE_MEMBER_REGION("REGION402", "기본 활동 지역이 존재하지 않습니다."),
	INVALID_RANGE("REGION403", "활동 지역 범위 단위(3,5,7,10)에 맞지 않습니다.");

	private final String status;
	private final String message;

}
