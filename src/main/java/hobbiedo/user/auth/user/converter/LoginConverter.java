package hobbiedo.user.auth.user.converter;

import hobbiedo.user.auth.user.dto.request.LoginRequestDTO;
import hobbiedo.user.auth.user.vo.request.LoginRequestVO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginConverter {
	public static LoginRequestDTO toRequestDTO(LoginRequestVO loginVO) {
		return LoginRequestDTO.builder()
				.loginId(loginVO.getLoginId())
				.password(loginVO.getPassword())
				.build();
	}
}
