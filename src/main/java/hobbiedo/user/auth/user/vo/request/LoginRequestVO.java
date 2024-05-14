package hobbiedo.user.auth.user.vo.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginRequestVO {
	private final String username;
	private final String password;
}
