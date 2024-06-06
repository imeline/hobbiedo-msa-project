package hobbiedo.survey.vo.request;

import java.util.ArrayList;
import java.util.List;

import hobbiedo.survey.dto.response.HobbySurveyResponseDto;
import hobbiedo.survey.type.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "취미 추천 설문 질문 정보")
public class HobbyQuestionRequestVo {

	private Long questionId;
	private String question;
	private QuestionType questionType;

	public HobbyQuestionRequestVo(Long questionId, String question, QuestionType questionType) {
		this.questionId = questionId;
		this.question = question;
		this.questionType = questionType;
	}

	public static List<HobbyQuestionRequestVo> hobbySurveyDtoToVo(
		List<HobbySurveyResponseDto> hobbySurveyDtoList) {

		List<HobbyQuestionRequestVo> getHobbyQuestions = new ArrayList<>();

		for (HobbySurveyResponseDto hobbySurveyDto : hobbySurveyDtoList) {
			getHobbyQuestions.add(new HobbyQuestionRequestVo(
				hobbySurveyDto.getQuestionId(),
				hobbySurveyDto.getQuestion(),
				hobbySurveyDto.getQuestionType()
			));
		}

		return getHobbyQuestions;
	}
}
