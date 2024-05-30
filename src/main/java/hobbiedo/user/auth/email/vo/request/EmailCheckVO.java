package hobbiedo.user.auth.email.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "이메일 인증 코드 일치 확인 요청 VO")
public class EmailCheckVO {
	@Schema(description = "코드 일치를 확인할 이메일")
	private String email;
	@Schema(description = "이메일 인증 코드")
	private String authCode;
}
