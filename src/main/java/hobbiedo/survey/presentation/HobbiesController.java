package hobbiedo.survey.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.global.api.ApiResponse;
import hobbiedo.global.api.code.status.SuccessStatus;
import hobbiedo.survey.application.HobbyService;
import hobbiedo.survey.dto.response.GetUserHobbyDto;
import hobbiedo.survey.vo.response.GetUserHobbyCardVo;
import hobbiedo.survey.vo.response.GetUserHobbyVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@Tag(name = "Hobbies", description = "취미 서비스")
public class HobbiesController {

	private final HobbyService hobbyService;

	@GetMapping("/hobbies")
	@Operation(summary = "회원 취미 조회", description = "회원의 취미 리스트를 조회합니다.")
	public ApiResponse<List<GetUserHobbyVo>> getUserHobbies(
		@RequestHeader(name = "Uuid") String uuid) {

		List<GetUserHobbyDto> userHobbies = hobbyService.getUserHobbies(uuid);

		return ApiResponse.onSuccess(
			SuccessStatus.GET_USER_HOBBIES_SUCCESS,
			GetUserHobbyVo.userHobbyDtoToVo(userHobbies)
		);
	}

	@GetMapping("/hobby-cards")
	@Operation(summary = "회원 취미 카드 조회", description = "회원의 취미 카드 리스트를 조회합니다.")
	public ApiResponse<List<GetUserHobbyCardVo>> getUserHobbyCards(
		@RequestHeader(name = "Uuid") String uuid) {

		List<GetUserHobbyDto> userHobbies = hobbyService.getUserHobbies(uuid);

		return ApiResponse.onSuccess(
			SuccessStatus.GET_USER_HOBBY_CARDS_SUCCESS,
			GetUserHobbyCardVo.userHobbyDtoToCardVo(userHobbies)
		);
	}
}
