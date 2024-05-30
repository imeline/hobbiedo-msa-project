package hobbiedo.user.auth.member.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "ReIssue API Body 요청 값")
public class ReIssueRequestVO {
	@Schema(description = "액세스 토큰 재발급을 위한 리프레시 토큰")
	@Pattern(regexp = "^Bearer [A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$",
		message = "유효하지 않은 JWT 토큰 형식입니다.")
	private String refreshToken;
}
