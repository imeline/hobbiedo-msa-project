package hobbiedo.survey.vo.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "취미 추천 설문 질문 응답 리스트")
public class HobbyQuestionRequestListVo {

	List<HobbyQuestionRequestVo> hobbyQuestionRequestVoList;

	public HobbyQuestionRequestListVo(List<HobbyQuestionRequestVo> hobbyQuestionRequestVoList) {
		this.hobbyQuestionRequestVoList = hobbyQuestionRequestVoList;
	}
}
