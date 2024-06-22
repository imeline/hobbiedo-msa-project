package hobbiedo.crew.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.crew.application.CrewService;
import hobbiedo.crew.dto.request.CrewModifyDTO;
import hobbiedo.crew.dto.request.CrewOutDTO;
import hobbiedo.crew.dto.request.CrewRequestDTO;
import hobbiedo.crew.dto.response.CrewDetailDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;
import hobbiedo.crew.dto.response.CrewNameDTO;
import hobbiedo.crew.dto.response.CrewProfileDTO;
import hobbiedo.crew.dto.response.CrewResponseDTO;
import hobbiedo.global.base.BaseResponse;
import hobbiedo.global.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "소모임", description = "Crew API")
@RequestMapping("/v1/users/crew")
public class CrewController {

	private final CrewService crewService;

	@Operation(summary = "소모임 생성", description = "소모임을 생성한다.(대표 사진은 아예 안보내도 되고 해시태그는 []로, 있으면 5개까지 가능")
	@PostMapping
	public BaseResponse<CrewIdDTO> createCrew(@Valid @RequestBody CrewRequestDTO crewDTO,
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

	@Operation(summary = "취미와 활동 지역에 해당하는 소모임 정보 목록 조회", description = "취미와 활동 지역에 해당하는 소모임 정보 목록을 조회한다.(순서 랜덤)")
	@GetMapping("/info/{hobbyId}/{regionId}")
	public BaseResponse<List<CrewDetailDTO>> getCrewInfoList(@PathVariable long hobbyId,
		@PathVariable long regionId) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_CREW_INFO_LIST,
			crewService.getCrewInfoList(hobbyId, regionId));
	}

	@Operation(summary = "소모임 정보 조회", description = "소모임 ID를 통해 소모임 정보를 조회한다.")
	@GetMapping("/{crewId}")
	public BaseResponse<CrewDetailDTO> getCrewInfo(@PathVariable long crewId) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_CREW_INFO,
			crewService.getCrewInfo(crewId));
	}

	@Operation(summary = "취미와 활동 지역에 해당하는 소모임 아이디 목록 조회", description = "취미와 활동 지역에 해당하는 소모임 아이디 목록을 조회한다.(순서 랜덤)")
	@GetMapping("/id/{hobbyId}/{regionId}")
	public BaseResponse<List<CrewIdDTO>> getCrewIdList(@PathVariable long hobbyId,
		@PathVariable long regionId) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_CREWS_BY_HOBBY_AND_REGION,
			crewService.getCrewsByHobbyAndRegion(hobbyId, regionId));
	}

	@Operation(summary = "한 유저가 가입한 소모임 프로필 목록 조회", description = "한 유저가 가입한 소모임 프로필 목록을 조회한다.")
	@GetMapping("/list/profile")
	public BaseResponse<List<CrewProfileDTO>> getCrewProfileList(
		@RequestHeader(name = "Uuid") String uuid) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_CREW_PROFILE_LIST,
			crewService.getCrewProfiles(uuid));
	}

	@Operation(summary = "소모임 이름 조회", description = "소모임 ID를 통해 소모임 이름을 조회한다.")
	@GetMapping("/name/{crewId}")
	public BaseResponse<CrewNameDTO> getCrewName(@PathVariable long crewId) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_CREW_NAME,
			crewService.getCrewName(crewId));
	}

	@Operation(summary = "소모임 탈퇴", description = "소모임에서 회원을 탈퇴시키고 소모임 인원을 -1 한다.")
	@DeleteMapping("/withdrawal/{crewId}")
	public BaseResponse<Void> withdrawalCrew(@PathVariable long crewId,
		@RequestHeader(name = "Uuid") String uuid) {
		crewService.exitCrew(crewId, uuid);
		return BaseResponse.onSuccess(SuccessStatus.WITHDRAWAL_CREW, null);
	}

	@Operation(summary = "소모임 정보 수정화면 조회", description = "소모임 정보 수정화면을 조회한다.")
	@GetMapping("/modify-view/{crewId}")
	public BaseResponse<CrewResponseDTO> getCrewModifyView(@PathVariable long crewId) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_CREW_MODIFY_VIEW,
			crewService.getCrew(crewId));
	}

	@Operation(summary = "소모임 정보 수정", description = "소모임 정보를 수정한다.")
	@PutMapping("/{crewId}")
	public BaseResponse<Void> modifyCrew(@Valid @RequestBody CrewModifyDTO crewModifyDTO,
		@PathVariable long crewId, @RequestHeader(name = "Uuid") String uuid) {
		crewService.modifyCrew(crewModifyDTO, crewId, uuid);
		return BaseResponse.onSuccess(SuccessStatus.MODIFY_CREW, null);
	}

	@Operation(summary = "소모임 강제 퇴장", description = "소모임에서 회원을 강제로 퇴장시킨다.(블랙리스트로 변경)")
	@PostMapping("/forced-exit/{crewId}")
	public BaseResponse<Void> forcedExitCrew(@Valid @RequestBody CrewOutDTO crewOutDTO,
		@PathVariable long crewId,
		@RequestHeader(name = "Uuid") String uuid) {
		crewService.forcedExitCrew(crewOutDTO, crewId, uuid);
		return BaseResponse.onSuccess(SuccessStatus.FORCED_EXIT_CREW, null);
	}

}

