package hobbiedo.user.auth.email.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "이메일 인증 코드 생성 요청 VO")
public class EmailAuthVO {
	@Schema(description = "이메일 인증 코드를 발송할 email")
	private String email;
}
