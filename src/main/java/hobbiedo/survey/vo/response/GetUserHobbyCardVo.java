package hobbiedo.survey.vo.response;

import java.util.ArrayList;
import java.util.List;

import hobbiedo.survey.dto.response.GetUserHobbyDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "저장된 회원의 취미 카드 정보")
public class GetUserHobbyCardVo {

	private Long hobbyId;
	private String hobbyName;
	private String description;
	private String imageUrl;
	private Integer fitRate;

	public GetUserHobbyCardVo(Long hobbyId, String hobbyName, String description, String imageUrl,
		Integer fitRate) {
		this.hobbyId = hobbyId;
		this.hobbyName = hobbyName;
		this.description = description;
		this.imageUrl = imageUrl;
		this.fitRate = fitRate;
	}

	public static List<GetUserHobbyCardVo> userHobbyDtoToCardVo(
		List<GetUserHobbyDto> userHobbyDtoList) {

		List<GetUserHobbyCardVo> getUserHobbyCardVoList = new ArrayList<>();

		// 리스트의 크기가 5보다 작은 경우, toIndex 에는 리스트의 크기가 들어간다.
		int toIndex = Math.min(userHobbyDtoList.size(), 5);

		// 0부터 4번째 요소까지만 가져오기
		List<GetUserHobbyDto> subList = userHobbyDtoList.subList(0, toIndex);

		for (GetUserHobbyDto userHobbyDto : subList) {
			getUserHobbyCardVoList.add(new GetUserHobbyCardVo(
				userHobbyDto.getHobbyId(),
				userHobbyDto.getHobbyName(),
				userHobbyDto.getDescription(),
				userHobbyDto.getImageUrl(),
				userHobbyDto.getFitRate()
			));
		}

		return getUserHobbyCardVoList;
	}
}
