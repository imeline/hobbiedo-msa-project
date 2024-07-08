package hobbiedo.user.auth.member.converter;

import hobbiedo.user.auth.member.dto.request.ResetPasswordDTO;
import hobbiedo.user.auth.member.vo.request.ResetPasswordVO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResetPasswordConverter {
	public static ResetPasswordDTO toDTO(ResetPasswordVO resetPasswordVO) {
		return ResetPasswordDTO.builder()
			.name(resetPasswordVO.getName())
			.email(resetPasswordVO.getEmail())
			.loginId(resetPasswordVO.getLoginId())
			.build();
	}
}
