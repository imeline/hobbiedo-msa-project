package hobbiedo.user.auth.google.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.user.auth.global.api.code.status.ErrorStatus;
import hobbiedo.user.auth.global.exception.MemberExceptionHandler;
import hobbiedo.user.auth.google.domain.SocialAuth;
import hobbiedo.user.auth.google.dto.request.GoogleLoginDTO;
import hobbiedo.user.auth.google.dto.request.GoogleSignUpDTO;
import hobbiedo.user.auth.google.infrastructure.GoogleMemberRepository;
import hobbiedo.user.auth.google.infrastructure.SocialAuthRepository;
import hobbiedo.user.auth.kafka.application.KafkaProducerService;
import hobbiedo.user.auth.kafka.dto.SignUpDTO;
import hobbiedo.user.auth.member.application.AuthService;
import hobbiedo.user.auth.member.domain.Member;
import hobbiedo.user.auth.member.infrastructure.MemberRepository;
import hobbiedo.user.auth.member.vo.response.LoginResponseVO;
import hobbiedo.user.auth.member.vo.response.SignUpVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleService {

	private final GoogleMemberRepository googleMemberRepository;
	private final SocialAuthRepository socialAuthRepository;
	private final AuthService authService;
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final KafkaProducerService kafkaProducerService;

	@Transactional
	public LoginResponseVO loginGoogle(GoogleLoginDTO googleLoginDTO) {
		// 일반 회원인지 판별
		Member member = googleMemberRepository.findByEmail(googleLoginDTO.getEmail())
			.orElseThrow(() -> new MemberExceptionHandler(ErrorStatus.NO_EXIST_MEMBER));
		// 구글 회원가입이 되어있지 않은 경우
		if (!socialAuthRepository.existsByMember(member)) {
			createSocialAuth(member, googleLoginDTO.getExternalId(), "GOOGLE");
		}

		return authService.getLoginResponse(member.getUuid());
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
	public SignUpVO signUpGoogle(GoogleSignUpDTO googleSignUpDTO) {
		validateLoginId(googleSignUpDTO);
		// 일반 회원 가입
		Member member = googleSignUpDTO.toMemberEntity();
		googleMemberRepository.save(member);
		memberRepository.save(googleSignUpDTO.toIntegrateAuthEntity(member,
			passwordEncoder.encode(googleSignUpDTO.getPassword())));
		// 구글 회원 가입
		createSocialAuth(member, googleSignUpDTO.getExternalId(), "GOOGLE");
		kafkaProducerService.setSignUpTopic(SignUpDTO.toDto(member.getUuid(), member.getName()));
		return SignUpVO.builder()
			.uuid(member.getUuid())
			.build();
	}

	private void validateLoginId(GoogleSignUpDTO googleSignUpDTO) {
		if (memberRepository.existsByMember_Email(googleSignUpDTO.getEmail())) {
			throw new MemberExceptionHandler(ErrorStatus.ALREADY_USE_EMAIL);
		}
		if (memberRepository.existsByMember_PhoneNumber(googleSignUpDTO.getPhoneNumber())) {
			throw new MemberExceptionHandler(ErrorStatus.ALREADY_USE_PHONE_NUMBER);
		}
	}
}
