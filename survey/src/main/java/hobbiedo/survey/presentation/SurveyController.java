package hobbiedo.survey.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.global.api.ApiResponse;
import hobbiedo.global.api.code.status.SuccessStatus;
import hobbiedo.survey.application.SurveyService;
import hobbiedo.survey.dto.request.HobbySurveyRequestDto;
import hobbiedo.survey.dto.response.HobbySurveyResponseDto;
import hobbiedo.survey.vo.request.HobbyQuestionRequestListVo;
import hobbiedo.survey.vo.response.HobbyQuestionResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/survey")
@Tag(name = "Survey", description = "취미 추천 설문 서비스")
public class SurveyController {

	private final SurveyService surveyService;

	@GetMapping("/questions")
	@Operation(summary = "취미 추천 설문 조회", description = "취미 추천 설문 질문 리스트를 조회합니다.")
	public ApiResponse<List<HobbyQuestionResponseVo>> getHobbySurvey() {

		List<HobbySurveyResponseDto> hobbySurveyQuestions = surveyService.getHobbySurveyQuestions();

		return ApiResponse.onSuccess(
			SuccessStatus.GET_HOBBY_SURVEY_SUCCESS,
			HobbyQuestionResponseVo.hobbySurveyDtoToVo(hobbySurveyQuestions)
		);
	}

	@PostMapping("/answers")
	@Operation(summary = "취미 추천 설문 응답", description = "취미 추천 설문에 대한 응답을 저장합니다.")
	public ApiResponse<Void> saveHobbySurveyAnswers(
		@RequestHeader(name = "Uuid") String uuid,
		@RequestBody HobbyQuestionRequestListVo hobbySurveyAnswers) {

		List<HobbySurveyRequestDto> hobbySurveyRequestDtoList = hobbySurveyAnswers
			.getHobbyQuestionRequestVoList()
			.stream()
			.map(HobbySurveyRequestDto::hobbyQuestionVoToDto)
			.toList();

		surveyService.saveOrUpdateHobbyData(uuid, hobbySurveyRequestDtoList);

		return ApiResponse.onSuccess(
			SuccessStatus.SAVE_USER_HOBBY_SUCCESS
		);
	}
}
