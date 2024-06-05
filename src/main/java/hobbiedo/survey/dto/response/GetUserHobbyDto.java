package hobbiedo.survey.dto.response;

import hobbiedo.survey.domain.UserHobby;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder // 빌더는 객체를 생성할 때 사용하는데, 생성자를 사용하지 않고 객체를 생성할 수 있다.
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserHobbyDto {

	private Long id;
	private String name;
	private String description;
	private String imageUrl;
	private Integer fitRate;

	public static GetUserHobbyDto userHobbyToDto(UserHobby userHobby) {
		return GetUserHobbyDto.builder()
			.id(userHobby.getHobby().getId())
			.name(userHobby.getHobby().getName())
			.description(userHobby.getHobby().getDescription())
			.imageUrl(userHobby.getHobby().getImageUrl())
			.fitRate(userHobby.getFitRate())
			.build();
	}
}
