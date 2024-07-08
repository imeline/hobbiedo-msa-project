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
public class UserHobbyResponseDto {

	private Long hobbyId;
	private String hobbyName;
	private String description;
	private String imageUrl;
	private Integer fitRate;

	public static UserHobbyResponseDto userHobbyToDto(UserHobby userHobby) {
		return UserHobbyResponseDto.builder()
			.hobbyId(userHobby.getHobby().getId()) // UserHobby 객체의 Hobby 객체의 id 를 가져온다.
			.hobbyName(userHobby.getHobby().getName()) // UserHobby 객체의 Hobby 객체의 nam 을 가져온다.
			.description(userHobby.getHobby().getDescription())
			.imageUrl(userHobby.getHobby().getImageUrl())
			.fitRate(userHobby.getFitRate()) // UserHobby 객체의 fitRate 를 가져온다.
			.build();
	}
}
