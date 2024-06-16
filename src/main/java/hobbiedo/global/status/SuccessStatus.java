package hobbiedo.global.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessStatus { //메시지를 중앙에서 관리하는 것이 유지 보수와 일관성 측면에서 유리
	// 채팅
	FIND_CHAT_HISTORY("이전 채팅 내용 조회에 성공하였습니다."),
	FIND_IMAGE_CHAT("사진 모아보기 조회에 성공하였습니다."),
	FIND_LAST_CHAT_LIST("처음 채팅방 리스트 조회에 성공하였습니다."),

	// 활동지역
	CREATE_REGION("활동 지역을 등록에 성공하였습니다."),
	FIND_ADDRESS_NAME_LIST("활동 지역 이름명 리스트 조회에 성공하였습니다."),
	FIND_REGION_DETAIL("한 활동 지역 정보 조회에 성공하였습니다."),
	FIND_BASE_ADDRESS_NAME("기본 활동 지역 이름명 조회에 성공하였습니다."),
	UPDATE_REGION("활동 지역 수정 성공하였습니다."),
	DELETE_REGION("활동 지역 삭제 성공하였습니다."),
	CHANGE_BASE_REGION("기본 활동 지역 변경에 성공하였습니다."),
	FIND_REGION_XY("소모임 생성 시, 활동 지역 목록 조회에 성공하였습니다."),
	CREATE_FIRST_REGION("최초 활동 지역 등록에 성공하였습니다."),

	// 소모임
	CREATE_CREW("소모임 생성에 성공하였습니다."),
	JOIN_FREE_CREW("자유 방식 소모임에 가입 완료되었습니다."),
	FIND_CREW_INFO("소모임 정보 조회에 성공하였습니다."),
	FIND_CREWS_BY_HOBBY_AND_REGION("취미와 활동 지역에 해당하는 소모임 목록 조회에 성공하였습니다."),
	SUBMISSION_JOIN_FORM("소모임 가입 신청서 제출에 성공하였습니다."),
	FIND_CREW_PROFILE_LIST("가입한 소모임 목록 조회 조회에 성공하였습니다."),
	FIND_CREW_NAME("소모임 이름 조회에 성공하였습니다."),
	WITHDRAWAL_CREW("소모임 탈퇴에 성공하였습니다."),
	FIND_CREW_MODIFY_VIEW("소모임 정보 수정화면 조회에 성공하였습니다."),
	MODIFY_CREW("소모임 정보 수정에 성공하였습니다."),
	FORCED_EXIT_CREW("소모임 강제 퇴장에 성공하였습니다."),
	FIND_JOIN_FORM_LIST("한 소모임의 가입 신청서 목록 조회에 성공하였습니다."),
	FIND_JOIN_FORM("소모임 가입 신청서 상세 조회에 성공하였습니다."),
	ACCEPT_JOIN_FORM("소모임 가입 신청서 수락에 성공하였습니다."),;

	private final String status;
}
