package hobbiedo.survey.vo.response;

import java.util.ArrayList;
import java.util.List;

import hobbiedo.survey.dto.response.UserHobbyResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "저장된 회원의 취미 정보")
public class UserHobbyResponseVo {

	private Long hobbyId;
	private String hobbyName;

	public UserHobbyResponseVo(Long hobbyId, String hobbyName) {
		this.hobbyId = hobbyId;
		this.hobbyName = hobbyName;
	}

	public static List<UserHobbyResponseVo> userHobbyDtoToVo(
		List<UserHobbyResponseDto> userHobbyDtoList) {

		List<UserHobbyResponseVo> getUserHobbyVoList = new ArrayList<>();

		// 리스트의 크기가 5보다 작은 경우, toIndex 에는 리스트의 크기가 들어간다.
		int toIndex = Math.min(userHobbyDtoList.size(), 5);

		// 0부터 4번째 요소까지만 가져오기
		List<UserHobbyResponseDto> subList = userHobbyDtoList.subList(0, toIndex);

		for (UserHobbyResponseDto userHobbyDto : subList) {
			getUserHobbyVoList.add(new UserHobbyResponseVo(
				userHobbyDto.getHobbyId(),
				userHobbyDto.getHobbyName()
			));
		}

		return getUserHobbyVoList;
	}
}
