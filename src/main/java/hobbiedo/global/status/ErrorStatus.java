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
	NO_EXIST_REGION("REGION401", "활동 지역이 존재하지 않습니다."),
	NO_EXIST_BASE_REGION("REGION402", "기본 활동 지역이 존재하지 않습니다."),
	INVALID_RANGE("REGION403", "활동 지역 범위 단위(3,5,7,10)에 맞지 않습니다."),

	// CREW 소모임
	INVALID_HASH_TAG_COUNT("CREW401", "해시태그는 5개까지 등록 가능합니다."),
	INVALID_JOIN_TYPE("CREW402", "가입 유형이 올바르지 않습니다."),
	INVALID_MAX_HOST_COUNT("CREW403", "한 회원은 최대 5개 소모임 생성이 가능 합니다."),
	NO_EXIST_CREW("CREW404", "해당 소모임이 존재하지 않습니다."),
	ALREADY_JOINED_CREW("CREW405", "이미 가입된 소모임입니다."),
	INVALID_BANNED("CREW406", "해당 소모임에 가입할 수 없는 회원입니다."),
	INVALID_MAX_PARTICIPANT("CREW407", "현재 소모임은 최대 인원 100명을 충족한 상태입니다."),
	ALREADY_SEND_JOIN_FORM("CREW408", "이미 해당 소모임에 가입 신청을 보냈습니다."),
	NO_EXIST_CREW_MEMBER("CREW409", "해당 소모임 회원이 존재하지 않습니다."),
	INVALID_HOST_WITHDRAWAL("CREW410", "방장은 소모임을 탈퇴할 수 없습니다."),
	INVALID_HOST_ACCESS("CREW411", "방장만 접근 가능한 페이지입니다."),;

	private final String status;
	private final String message;

}
