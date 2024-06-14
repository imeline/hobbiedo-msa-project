package hobbiedo.crew.presentation;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.crew.application.CrewService;
import hobbiedo.crew.dto.request.CrewDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;
import hobbiedo.global.base.BaseResponse;
import hobbiedo.global.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "소모임", description = "Crew API")
@RequestMapping("/v1/users/crew")
public class CrewController {

	private final CrewService crewService;

	@Operation(summary = "소모임 생성", description = "소모임을 생성한다.(대표 사진은 아예 안보내도 되고 해시태그는 []로, 있으면 5개까지 가능")
	@PostMapping
	public BaseResponse<CrewIdDTO> createCrew(@RequestBody CrewDTO crewDTO,
		@RequestHeader(name = "Uuid") String uuid) {
		return BaseResponse.onSuccess(SuccessStatus.CREATE_CREW,
			crewService.createCrew(crewDTO, uuid));
	}

	@Operation(summary = "(자유 방식) 소모임 가입", description = "자유 방식 소모임에 새로운 회원을 추가한다.")
	@PostMapping("/join/{crewId}")
	public BaseResponse<Void> joinFreeCrew(@PathVariable long crewId,
		@RequestHeader(name = "Uuid") String uuid) {
		crewService.joinCrew(crewId, uuid);
		return BaseResponse.onSuccess(SuccessStatus.JOIN_FREE_CREW, null);
	}

}
