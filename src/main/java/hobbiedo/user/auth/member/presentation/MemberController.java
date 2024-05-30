package hobbiedo.user.auth.member.presentation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.user.auth.email.application.EmailService;
import hobbiedo.user.auth.global.api.ApiResponse;
import hobbiedo.user.auth.global.api.code.status.SuccessStatus;
import hobbiedo.user.auth.member.application.MemberService;
import hobbiedo.user.auth.member.converter.FindLoginIdConverter;
import hobbiedo.user.auth.member.converter.ResetPasswordConverter;
import hobbiedo.user.auth.member.converter.SignUpConverter;
import hobbiedo.user.auth.member.dto.response.ResetPasswordDTO;
import hobbiedo.user.auth.member.vo.request.FindLoginIdVO;
import hobbiedo.user.auth.member.vo.request.IntegrateSignUpVO;
import hobbiedo.user.auth.member.vo.request.ResetPasswordVO;
import hobbiedo.user.auth.member.vo.response.CheckLoginIdVO;
import hobbiedo.user.auth.member.vo.response.SignUpVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/non-users")
@Tag(name = "Member", description = "사용자 서비스")
public class MemberController {
	public static final String LOGIN_ID_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$";
	private final MemberService memberService;
	private final EmailService idEmailService;
	private final EmailService passwordEmailService;

	public MemberController(MemberService memberService,
		@Qualifier(value = "idEmailService") EmailService idEmailService,
		@Qualifier(value = "passwordEmailService") EmailService passwordEmailService) {
		this.memberService = memberService;
		this.idEmailService = idEmailService;
		this.passwordEmailService = passwordEmailService;
	}

	@PostMapping("/sign-up")
	@Operation(summary = "통합 회원가입",
		description = "통합 회원가입을 진행하여 새로운 회원을 등록합니다.")
	public ApiResponse<SignUpVO> integrationSignUpApi(@RequestBody @Valid IntegrateSignUpVO signUpVO) {
		return ApiResponse.onSuccess(
			SuccessStatus.INTEGRATE_SIGN_UP_SUCCESS,
			memberService.integrateSignUp(SignUpConverter.toDTO(signUpVO))
		);
	}

	@PostMapping("/user-id")
	@Operation(summary = "회원 아이디 찾기",
		description = "회원 이름, 이메일을 통해 로그인 아이디를 찾습니다.")
	public ApiResponse<Void> findLoginIdApi(
		@RequestBody FindLoginIdVO findLoginIdVO) {
		idEmailService.sendMail(FindLoginIdConverter.toDTO(findLoginIdVO));

		return ApiResponse.onSuccess(
			SuccessStatus.FIND_LOGIN_ID_SUCCESS,
			null
		);
	}

	@PostMapping("/user-password")
	@Operation(summary = "회원 비밀번호 찾기(임시 비밀번호 발급)",
		description = "회원의 이름,이메일,아이디를 검증하여 임시 비밀번호를 발급합니다.")
	public ApiResponse<Void> resetPasswordApi(
		@RequestBody ResetPasswordVO resetPasswordVO) {
		ResetPasswordDTO resetPasswordDTO = memberService.resetPassword(ResetPasswordConverter.toDTO(resetPasswordVO));
		passwordEmailService.sendMail(resetPasswordDTO);

		return ApiResponse.onSuccess(
			SuccessStatus.RESET_PASSWORD_SUCCESS,
			null
		);
	}

	@GetMapping("/duplication")
	@Operation(summary = "아이디 중복 확인",
		description = "해당 아이디가 중복인지 확인합니다.")
	public ApiResponse<CheckLoginIdVO> checkIdApi(
		@RequestParam("loginId")
		@Pattern(regexp = LOGIN_ID_PATTERN,
			message = "아이디는 8~20자리의 영어+숫자로만 이뤄져야합니다.(특수 문자x)") String loginId) {
		return ApiResponse.onSuccess(
			SuccessStatus.CAN_USE_LOGIN_ID,
			memberService.isDuplicated(loginId)
		);
	}
}
