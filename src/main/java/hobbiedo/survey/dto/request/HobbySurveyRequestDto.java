package hobbiedo.survey.dto.request;

import hobbiedo.survey.type.QuestionType;
import hobbiedo.survey.vo.request.HobbyQuestionRequestVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HobbySurveyRequestDto {

	private Long questionId;
	private QuestionType questionType;
	private Integer answer;

	// Vo 객체를 Dto 객체로 변환
	public static HobbySurveyRequestDto hobbyQuestionVoToDto(
		HobbyQuestionRequestVo hobbyQuestionRequestVo) {

		return HobbySurveyRequestDto.builder()
			.questionId(hobbyQuestionRequestVo.getQuestionId())
			.questionType(hobbyQuestionRequestVo.getQuestionType())
			.answer(hobbyQuestionRequestVo.getAnswer())
			.build();
	}
}
