package hobbiedo.survey.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.global.api.ApiResponse;
import hobbiedo.global.api.code.status.SuccessStatus;
import hobbiedo.survey.application.SurveyService;
import hobbiedo.survey.dto.response.HobbySurveyResponseDto;
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
}
