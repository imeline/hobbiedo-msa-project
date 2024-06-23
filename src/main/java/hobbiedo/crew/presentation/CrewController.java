package hobbiedo.crew.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.crew.application.ReplicaCrewService;
import hobbiedo.crew.dto.response.CrewMemberDTO;
import hobbiedo.global.api.ApiResponse;
import hobbiedo.global.api.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@Tag(name = "Crew", description = "소모임 서비스")
public class CrewController {

	private final ReplicaCrewService crewService;

	@Operation(summary = "소모임 회원들의 프로필 목록 조회", description = "한 소모임 회원들의 프로필을 조회한다.")
	@GetMapping("/crew/member/profiles/{crewId}")
	public ApiResponse<List<CrewMemberDTO>> getCrewMembersProfile(@PathVariable long crewId,
		@RequestHeader(name = "Uuid") String uuid) {
		return ApiResponse.onSuccess(SuccessStatus.GET_CREW_MEMBERS_PROFILE,
			crewService.getCrewMembers(crewId, uuid));
	}

}
