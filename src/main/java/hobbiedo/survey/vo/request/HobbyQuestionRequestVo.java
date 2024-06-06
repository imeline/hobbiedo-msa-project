package hobbiedo.survey.vo.request;

import hobbiedo.survey.type.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "취미 추천 설문 질문 응답")
public class HobbyQuestionRequestVo {

	private Long questionId;
	private QuestionType questionType;

	// 질문에 대한 답변
	private Integer answer;

	public HobbyQuestionRequestVo(Long questionId, QuestionType questionType, Integer answer) {
		this.questionId = questionId;
		this.questionType = questionType;
		this.answer = answer;
	}

}
