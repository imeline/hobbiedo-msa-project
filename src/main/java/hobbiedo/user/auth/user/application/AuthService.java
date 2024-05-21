package hobbiedo.user.auth.user.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.user.auth.global.api.code.status.ErrorStatus;
import hobbiedo.user.auth.global.api.exception.handler.MemberExceptionHandler;
import hobbiedo.user.auth.global.config.jwt.JwtUtil;
import hobbiedo.user.auth.global.config.jwt.TokenType;
import hobbiedo.user.auth.user.domain.RefreshToken;
import hobbiedo.user.auth.user.dto.request.LoginRequestDTO;
import hobbiedo.user.auth.user.dto.response.LoginResponseDTO;
import hobbiedo.user.auth.user.infrastructure.MemberRepository;
import hobbiedo.user.auth.user.infrastructure.RefreshTokenRepository;
import hobbiedo.user.auth.user.vo.response.LoginResponseVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
	private final JwtUtil jwtUtil;
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final RefreshTokenRepository refreshTokenRepository;

	public LoginResponseVO login(LoginRequestDTO loginRequestDTO) {
		LoginResponseDTO user = getUuidByLoginId(loginRequestDTO.getLoginId());
		validatePassword(loginRequestDTO.getPassword(), user.getPassword());

		String accessToken = jwtUtil.createJwt(user.getUuid(), TokenType.ACCESS_TOKEN);
		String refreshToken = jwtUtil.createJwt(user.getUuid(), TokenType.REFRESH_TOKEN);

		saveToRedis(refreshToken, TokenType.REFRESH_TOKEN, user.getUuid());
		return LoginResponseVO
				.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	private void saveToRedis(String refreshToken, TokenType tokenType, String uuid) {
		refreshTokenRepository.save(RefreshToken
				.builder()
				.refresh(refreshToken)
				.expiration(System.currentTimeMillis() + tokenType.getExpireTime())
				.id(uuid)
				.build());
	}

	private LoginResponseDTO getUuidByLoginId(String loginId) {
		return memberRepository
				.findByLoginId(loginId)
				.orElseThrow(() -> new MemberExceptionHandler(ErrorStatus.USER_INTEGRATED_LOGIN_FAIL));
	}

	private void validatePassword(String input, String origin) {
		if (!passwordEncoder.matches(input, origin)) {
			throw new MemberExceptionHandler(ErrorStatus.USER_INTEGRATED_LOGIN_FAIL);
		}
	}
}
