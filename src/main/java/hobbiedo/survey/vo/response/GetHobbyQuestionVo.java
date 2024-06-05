package hobbiedo.survey.vo.response;

import java.util.ArrayList;
import java.util.List;

import hobbiedo.survey.dto.response.GetHobbySurveyDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "취미 추천 설문 질문 정보")
public class GetHobbyQuestionVo {

	private Long questionId;
	private String question;

	public GetHobbyQuestionVo(Long questionId, String question) {
		this.questionId = questionId;
		this.question = question;
	}

	public static List<GetHobbyQuestionVo> hobbySurveyDtoToVo(
		List<GetHobbySurveyDto> hobbySurveyDtoList) {

		List<GetHobbyQuestionVo> getHobbyQuestions = new ArrayList<>();

		for (GetHobbySurveyDto hobbySurveyDto : hobbySurveyDtoList) {
			getHobbyQuestions.add(new GetHobbyQuestionVo(
				hobbySurveyDto.getQuestionId(),
				hobbySurveyDto.getQuestion()
			));
		}

		return getHobbyQuestions;
	}
}
