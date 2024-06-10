package hobbiedo.user.auth.google.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.user.auth.global.api.code.status.ErrorStatus;
import hobbiedo.user.auth.global.config.jwt.JwtUtil;
import hobbiedo.user.auth.global.config.jwt.TokenType;
import hobbiedo.user.auth.global.exception.MemberExceptionHandler;
import hobbiedo.user.auth.google.domain.SocialAuth;
import hobbiedo.user.auth.google.dto.request.GoogleLoginDTO;
import hobbiedo.user.auth.google.dto.request.GoogleSignUpDTO;
import hobbiedo.user.auth.google.infrastructure.GoogleMemberRepository;
import hobbiedo.user.auth.google.infrastructure.SocialAuthRepository;
import hobbiedo.user.auth.member.domain.Member;
import hobbiedo.user.auth.member.domain.RefreshToken;
import hobbiedo.user.auth.member.infrastructure.RefreshTokenRepository;
import hobbiedo.user.auth.member.vo.response.LoginResponseVO;
import hobbiedo.user.auth.member.vo.response.SignUpVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleService {

	private final GoogleMemberRepository memberRepository;
	private final SocialAuthRepository socialAuthRepository;
	private final JwtUtil jwtUtil;
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional
	public LoginResponseVO loginGoogle(GoogleLoginDTO googleLoginDTO) {
		// 일반 회원인지 판별
		Member member = memberRepository.findByNameAndEmail(googleLoginDTO.getName(),
				googleLoginDTO.getEmail())
			.orElseThrow(() -> new MemberExceptionHandler(ErrorStatus.NO_EXIST_MEMBER));
		// 구글 회원가입이 되어있지 않은 경우
		if (!socialAuthRepository.existsByMember(member)) {
			createSocialAuth(member, googleLoginDTO.getExternalId(), "GOOGLE");
		}

		return login(member);
	}

	@Transactional
	protected void createSocialAuth(Member member, String externalId, String socialType) {
		socialAuthRepository.save(SocialAuth.builder()
			.member(member)
			.externalId(externalId)
			.socialType(socialType)
			.build());
	}

	@Transactional
	protected LoginResponseVO login(Member member) {
		String accessToken = jwtUtil.createJwt(member.getUuid(), TokenType.ACCESS_TOKEN);
		String refreshToken = jwtUtil.createJwt(member.getUuid(), TokenType.REFRESH_TOKEN);

		saveToRedis(refreshToken, TokenType.REFRESH_TOKEN, member.getUuid());
		return LoginResponseVO
			.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	@Transactional
	protected void saveToRedis(String refreshToken, TokenType tokenType, String uuid) {
		refreshTokenRepository.save(RefreshToken
			.builder()
			.uuid(uuid)
			.refresh(refreshToken)
			.expiration(System.currentTimeMillis() + tokenType.getExpireTime())
			.build());
	}

	@Transactional
	public SignUpVO signUpGoogle(GoogleSignUpDTO googleSignUpDTO) {
		// 이미 사용중인 이메일인 경우
		if (memberRepository.existsByEmail(googleSignUpDTO.getEmail())) {
			throw new MemberExceptionHandler(ErrorStatus.ALREADY_USE_EMAIL);
		}
		// 일반 회원 가입
		Member member = googleSignUpDTO.toEntity();
		memberRepository.save(member);
		// 구글 회원 가입
		createSocialAuth(member, googleSignUpDTO.getExternalId(), "GOOGLE");

		return SignUpVO.builder()
			.uuid(member.getUuid())
			.build();
	}
}
