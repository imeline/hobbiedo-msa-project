package hobbiedo.user.auth.member.dto.request;

import hobbiedo.user.auth.member.vo.request.ProfileRequestVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {

	private String profileImageUrl;
	private String profileMessage;

	// Vo 객체를 Dto 객체로 변환
	public static ProfileRequestDto profileImageVoToDto(ProfileRequestVo profileImageRequestVo) {

		return ProfileRequestDto.builder()
				.profileImageUrl(profileImageRequestVo.getProfileImageUrl())
				.profileMessage(profileImageRequestVo.getProfileMessage())
				.build();
	}
}
