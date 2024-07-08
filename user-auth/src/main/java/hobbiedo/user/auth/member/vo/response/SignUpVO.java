package hobbiedo.user.auth.member.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "회원가입 시 반환값(VO)")
public class SignUpVO {
	@Schema(description = "해당 회원의 uuid")
	private final String uuid;
}
