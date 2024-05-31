package hobbiedo.user.auth.member.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.user.auth.email.util.RandomPasswordUtil;
import hobbiedo.user.auth.global.api.code.status.ErrorStatus;
import hobbiedo.user.auth.global.exception.MemberExceptionHandler;
import hobbiedo.user.auth.member.converter.SignUpConverter;
import hobbiedo.user.auth.member.domain.IntegrateAuth;
import hobbiedo.user.auth.member.domain.Member;
import hobbiedo.user.auth.member.dto.request.IntegrateSignUpDTO;
import hobbiedo.user.auth.member.dto.request.ResetPasswordDTO;
import hobbiedo.user.auth.member.dto.request.UpdatePasswordDTO;
import hobbiedo.user.auth.member.infrastructure.MemberRepository;
import hobbiedo.user.auth.member.vo.response.CheckLoginIdVO;
import hobbiedo.user.auth.member.vo.response.SignUpVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	public static final int MAX_PASSWORD_LENGTH = 20;

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public SignUpVO integrateSignUp(IntegrateSignUpDTO integrateSignUpDTO) {
		validateLoginId(integrateSignUpDTO);
		Member newMember = SignUpConverter.toEntity(integrateSignUpDTO);
		memberRepository.save(IntegrateAuth.builder()
			.loginId(integrateSignUpDTO.getLoginId())
			.password(passwordEncoder.encode(integrateSignUpDTO.getPassword()))
			.member(newMember)
			.build());

		return SignUpVO.builder()
			.uuid(newMember.getUuid())
			.build();
	}

	@Transactional
	public hobbiedo.user.auth.member.dto.response.ResetPasswordDTO resetPassword(ResetPasswordDTO resetPasswordDTO) {
		validateMember(resetPasswordDTO);
		return hobbiedo.user.auth.member.dto.response.ResetPasswordDTO.builder()
			.email(resetPasswordDTO.getEmail())
			.tempPassword(updatePassword(resetPasswordDTO))
			.build();
	}

	public CheckLoginIdVO isDuplicated(String loginId) {
		if (memberRepository.existsByLoginId(loginId)) {
			throw new MemberExceptionHandler(ErrorStatus.ALREADY_USE_LOGIN_ID);
		}

		return CheckLoginIdVO.builder()
			.isPossible(true)
			.build();
	}

	@Transactional
	public void update(UpdatePasswordDTO updatePasswordDTO) {
		validatePassword(updatePasswordDTO);
		String newPassword = updatePasswordDTO.getNewPassword();
		memberRepository.updatePasswordByUuid(
			passwordEncoder.encode(newPassword),
			updatePasswordDTO.getUuid());
	}

	private void validatePassword(UpdatePasswordDTO updatePasswordDTO) {
		String currentPassword = updatePasswordDTO.getCurrentPassword();
		String uuid = updatePasswordDTO.getUuid();

		String encoded = memberRepository.findPasswordByUuid(uuid);
		if (!passwordEncoder.matches(currentPassword, encoded)) {
			throw new MemberExceptionHandler(ErrorStatus.NOT_MATCH_PASSWORD);
		}

	}

	private void validateLoginId(IntegrateSignUpDTO integrateSignUpDTO) {
		if (memberRepository.existsByMember_Email(integrateSignUpDTO.getEmail())) {
			throw new MemberExceptionHandler(ErrorStatus.ALREADY_USE_EMAIL);
		}
		if (memberRepository.existsByMember_PhoneNumber(integrateSignUpDTO.getPhoneNumber())) {
			throw new MemberExceptionHandler(ErrorStatus.ALREADY_USE_PHONE_NUMBER);
		}
	}

	private void validateMember(ResetPasswordDTO resetPasswordDTO) {
		Boolean isExist = memberRepository.existPasswordBy(
			resetPasswordDTO.getName(),
			resetPasswordDTO.getEmail(),
			resetPasswordDTO.getLoginId());
		if (!isExist) {
			throw new MemberExceptionHandler(ErrorStatus.RESET_PASSWORD_FAIL);
		}
	}

	private String updatePassword(ResetPasswordDTO resetPasswordDTO) {
		String tempPassword = RandomPasswordUtil.generate(MAX_PASSWORD_LENGTH);
		String password = passwordEncoder.encode(tempPassword);

		memberRepository.updatePasswordByLoginId(password, resetPasswordDTO.getLoginId());
		return tempPassword;
	}

}
