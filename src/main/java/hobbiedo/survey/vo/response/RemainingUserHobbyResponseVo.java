package hobbiedo.survey.vo.response;

import java.util.ArrayList;
import java.util.List;

import hobbiedo.survey.dto.response.UserHobbyResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "저장된 회원의 나머지 취미 정보")
public class RemainingUserHobbyResponseVo {

	private Long hobbyId;
	private String hobbyName;

	public RemainingUserHobbyResponseVo(Long hobbyId, String hobbyName) {
		this.hobbyId = hobbyId;
		this.hobbyName = hobbyName;
	}

	public static List<RemainingUserHobbyResponseVo> userHobbyDtoToRemainingVo(
			List<UserHobbyResponseDto> userHobbyDtoList) {

		List<RemainingUserHobbyResponseVo> getRemainingUserHobbyVoList = new ArrayList<>();

		// 5번째 요소부터 마지막 요소까지만 가져오기
		List<UserHobbyResponseDto> subList = userHobbyDtoList.subList(5, userHobbyDtoList.size());

		for (UserHobbyResponseDto userHobbyDto : subList) {
			getRemainingUserHobbyVoList.add(new RemainingUserHobbyResponseVo(
					userHobbyDto.getHobbyId(),
					userHobbyDto.getHobbyName()
			));
		}

		return getRemainingUserHobbyVoList;
	}
}
