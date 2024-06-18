package hobbiedo.user.auth.member.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "로그인 API 응답값(VO)")
public class LoginResponseVO {
	@Schema(description = "액세스 토큰")
	private final String accessToken;
	@Schema(description = "리프레시 토큰")
	private final String refreshToken;
	private final String uuid;
}
