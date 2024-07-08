package hobbiedo.crew.dto.response;

import hobbiedo.crew.domain.JoinForm;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinFormResponseDTO {
	private String joinMessage;

	private String profileUrl;

	private String name;

	private String birthday;

	private String address;

	private String gender;

	public static JoinFormResponseDTO toDto(JoinForm joinForm) {
		return JoinFormResponseDTO.builder()
			.joinMessage(joinForm.getJoinMessage())
			.profileUrl(joinForm.getProfileUrl())
			.name(joinForm.getName())
			.birthday(joinForm.getBirthday())
			.address(joinForm.getAddress())
			.gender(joinForm.getGender())
			.build();
	}
}
