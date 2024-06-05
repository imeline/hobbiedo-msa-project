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
public class GetHobbySurveyDto {

	private Long questionId;
	private String question;
	private QuestionType questionType;

	public static GetHobbySurveyDto hobbySurveyToDto(HobbySurvey hobbySurvey) {
		return GetHobbySurveyDto.builder()
			.questionId(hobbySurvey.getId())
			.question(hobbySurvey.getQuestion())
			.questionType(hobbySurvey.getQuestionType())
			.build();
	}
}
