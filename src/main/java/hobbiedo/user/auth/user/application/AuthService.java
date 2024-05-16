package hobbiedo.user.auth.user.application;

import org.springframework.stereotype.Service;

import hobbiedo.user.auth.global.config.jwt.JwtUtil;
import hobbiedo.user.auth.user.dto.response.LoginResponseDTO;
import hobbiedo.user.auth.user.vo.request.LoginRequestVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final JwtUtil jwtUtil;

	public LoginResponseDTO login(LoginRequestVO loginVO) {
		String token = jwtUtil.createJwt(loginVO.getUsername(), 60 * 60 * 12L);

		return LoginResponseDTO
				.builder()
				.token(token)
				.build();
	}
}
