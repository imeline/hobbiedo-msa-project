package hobbiedo.user.auth.user.vo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginRequestVO {
	@NotBlank(message = "올바르지 않은 아이디입니다.")
	private final String loginId;
	@NotBlank(message = "올바르지 않은 아이디입니다.")
	private final String password;

}
