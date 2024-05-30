package hobbiedo.user.auth.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResetPasswordDTO {
	private final String email;
	private final String tempPassword;
}
