package hobbiedo.user.auth.user.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.user.auth.global.api.code.status.ErrorStatus;
import hobbiedo.user.auth.global.api.exception.handler.MemberExceptionHandler;
import hobbiedo.user.auth.global.config.jwt.JwtUtil;
import hobbiedo.user.auth.user.domain.TokenType;
import hobbiedo.user.auth.user.dto.response.LoginResponseDTO;
import hobbiedo.user.auth.user.infrastructure.MeberRepository;
import hobbiedo.user.auth.user.vo.request.LoginRequestVO;
import hobbiedo.user.auth.user.vo.response.LoginResponseVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
	private final JwtUtil jwtUtil;
	private final MeberRepository meberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public LoginResponseVO login(LoginRequestVO loginVO) {
		LoginResponseDTO user = getUuidByLoginId(loginVO.getLoginId());
		validatePassword(loginVO.getPassword(), user.getPassword());
		String accessToken = jwtUtil.createJwt(user.getUuid(), TokenType.ACCESS_TOKEN);
		String refreshToken = jwtUtil.createJwt(user.getUuid(), TokenType.REFRESH_TOKEN);

		return LoginResponseVO
				.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	private LoginResponseDTO getUuidByLoginId(String loginId) {
		return meberRepository
				.findByLoginId(loginId)
				.orElseThrow(() -> new MemberExceptionHandler(ErrorStatus.USER_INTEGRATED_LOGIN_FAIL));
	}

	private void validatePassword(String input, String origin) {
		if (!passwordEncoder.matches(input, origin)) {
			throw new MemberExceptionHandler(ErrorStatus.USER_INTEGRATED_LOGIN_FAIL);
		}
	}
}
