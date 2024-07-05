package hobbiedo.survey.dto.response;

import hobbiedo.survey.domain.HobbySurvey;
import hobbiedo.survey.type.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HobbySurveyResponseDto {

	private Long questionId;
	private String question;
	private QuestionType questionType;

	public static HobbySurveyResponseDto hobbySurveyToDto(HobbySurvey hobbySurvey) {
		return HobbySurveyResponseDto.builder()
			.questionId(hobbySurvey.getId())
			.question(hobbySurvey.getQuestion())
			.questionType(hobbySurvey.getQuestionType())
			.build();
	}
}
