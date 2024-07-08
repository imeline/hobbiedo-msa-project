package hobbiedo.user.auth.member.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "프로필 수정 요청")
public class ProfileRequestVo {

	private String profileImageUrl;
	private String profileMessage;

	public ProfileRequestVo(String profileImageUrl, String profileMessage) {
		this.profileImageUrl = profileImageUrl;
		this.profileMessage = profileMessage;
	}
}
