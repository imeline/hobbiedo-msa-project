package hobbiedo.crew.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.crew.application.JoinFormService;
import hobbiedo.crew.dto.request.JoinFormRequestDTO;
import hobbiedo.crew.dto.response.JoinFormListDTO;
import hobbiedo.crew.dto.response.JoinFormResponseDTO;
import hobbiedo.crew.dto.response.MyJoinFormDTO;
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

	private final JoinFormService joinFormService;

	@Operation(summary = "(가입 방식) 소모임 가입 신청서 제출", description = "가입 방식 소모임에 가입 신청서를 제출한다.")
	@PostMapping("/submission/join-form/{crewId}")
	public BaseResponse<Void> submissionJoinForm(@Valid @RequestBody JoinFormRequestDTO joinFormDTO,
		@PathVariable long crewId, @RequestHeader(name = "Uuid") String uuid) {
		joinFormService.addJoinForm(joinFormDTO, crewId, uuid);
		return BaseResponse.onSuccess(SuccessStatus.SUBMISSION_JOIN_FORM, null);
	}

	@Operation(summary = "한 소모임의 가입 신청서 목록 조회", description = "한 소모임에 온 가입 신청서 목록을 조회한다.")
	@GetMapping("/join-forms/{crewId}")
	public BaseResponse<List<JoinFormListDTO>> getJoinForms(@PathVariable long crewId,
		@RequestHeader(name = "Uuid") String uuid) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_JOIN_FORM_LIST,
			joinFormService.getJoinFormList(crewId, uuid));
	}

	@Operation(summary = "소모임 가입 신청서 상세 조회", description = "소모임 가입 신청서를 상세 조회한다.")
	@GetMapping("/join-form/{joinFormId}")
	public BaseResponse<JoinFormResponseDTO> getJoinForm(@PathVariable String joinFormId) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_JOIN_FORM,
			joinFormService.getJoinForm(joinFormId));
	}

	@Operation(summary = "소모임 가입 신청서 수락", description = "소모임 가입 신청서를 수락한다.")
	@PostMapping("/acceptance/join/{joinFormId}")
	public BaseResponse<Void> acceptJoinForm(@PathVariable String joinFormId,
		@RequestHeader(name = "Uuid") String uuid) {
		joinFormService.acceptJoinForm(joinFormId, uuid);
		return BaseResponse.onSuccess(SuccessStatus.ACCEPT_JOIN_FORM, null);
	}

	@Operation(summary = "소모임 가입 신청서 거절", description = "소모임 가입 신청서를 거절한다.")
	@DeleteMapping("/rejection/join/{joinFormId}")
	public BaseResponse<Void> rejectJoinForm(@PathVariable String joinFormId,
		@RequestHeader(name = "Uuid") String uuid) {
		joinFormService.rejectJoinForm(joinFormId, uuid);
		return BaseResponse.onSuccess(SuccessStatus.REJECT_JOIN_FORM, null);
	}

	@Operation(summary = "나의 소모임 가입신청서 리스트 조회", description = "나의 소모임 가입신청서 리스트를 조회한다.")
	@GetMapping("/my-join-forms")
	public BaseResponse<List<MyJoinFormDTO>> getMyJoinForms(
		@RequestHeader(name = "Uuid") String uuid) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_MY_JOIN_FORM_LIST,
			joinFormService.getMyJoinForms(uuid));
	}

	@Operation(summary = "소모임 가입 신청서 철회", description = "제출한 소모임 가입 신청서를 철회한다.")
	@DeleteMapping("/cancellation/join/{joinFormId}")
	public BaseResponse<Void> cancelJoinForm(@PathVariable String joinFormId,
		@RequestHeader(name = "Uuid") String uuid) {
		joinFormService.cancelJoinForm(joinFormId, uuid);
		return BaseResponse.onSuccess(SuccessStatus.CANCEL_JOIN_FORM, null);
	}
}

