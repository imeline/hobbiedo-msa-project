package hobbiedo.user.auth.member.vo.response;

import java.time.LocalDate;

import hobbiedo.user.auth.member.dto.response.ProfileResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "프로필 조회 응답")
public class ProfileResponseVo {

	private String profileImageUrl;
	private String name;
	private String profileMessage;
	private LocalDate birth;
	private String gender;

	public ProfileResponseVo(String profileImageUrl, String name, String profileMessage,
		LocalDate birth, String gender) {
		this.profileImageUrl = profileImageUrl;
		this.name = name;
		this.profileMessage = profileMessage;
		this.birth = birth;

		if (gender.equals("FEMALE")) {
			gender = "여성";
		} else if (gender.equals("MALE")) {
			gender = "남성";
		}

		this.gender = gender;
	}

	public static ProfileResponseVo profileDtoToProfileVo(ProfileResponseDto profileDto) {
		return new ProfileResponseVo(
			profileDto.getProfileImageUrl(),
			profileDto.getName(),
			profileDto.getProfileMessage(),
			profileDto.getBirth(),
			profileDto.getGender().name()
		);
	}
}
