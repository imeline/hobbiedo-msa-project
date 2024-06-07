package hobbiedo.global.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessStatus { //메시지를 중앙에서 관리하는 것이 유지 보수와 일관성 측면에서 유리
	// 채팅
	FIND_CHAT_HISTORY("이전 채팅 내용 조회에 성공하였습니다."),
	FIND_IMAGE_CHAT("사진 모아보기 조회에 성공하였습니다."),

	// 활동지역
	CREATE_REGION("활동 지역을 등록에 성공하였습니다."),
	FIND_ADDRESS_NAME_LIST("활동 지역 이름명 리스트 조회에 성공하였습니다."),
	FIND_REGION_DETAIL("한 활동 지역 정보 조회에 성공하였습니다."),
	FIND_BASE_ADDRESS_NAME("기본 활동 지역 이름명 조회에 성공하였습니다."),
	UPDATE_REGION("활동 지역 수정 성공하였습니다."),
	DELETE_REGION("활동 지역 삭제 성공하였습니다."),
	CHANGE_BASE_REGION("기본 활동 지역 변경에 성공하였습니다."),
	FIND_REGION_XY("소모임 생성 시, 활동 지역 목록 조회에 성공하였습니다."),
	CREATE_SING_UP_REGION("회원가입 시, 활동 지역 등록에 성공하였습니다.");

	private final String status;
}
