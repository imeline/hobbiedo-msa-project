package hobbiedo.user.auth.user.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "로그인에 필요한 정보(VO)")
public class LoginRequestVO {
	@Schema(description = "입력 로그인 아이디")
	@NotBlank(message = "올바르지 않은 아이디입니다.")
	private String loginId;

	@Schema(description = "입력 비밀번호")
	@NotBlank(message = "올바르지 않은 아이디입니다.")
	private String password;

}
