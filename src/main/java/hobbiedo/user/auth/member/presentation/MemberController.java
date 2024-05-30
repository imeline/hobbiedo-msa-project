package hobbiedo.user.auth.member.presentation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.user.auth.email.application.EmailService;
import hobbiedo.user.auth.global.api.ApiResponse;
import hobbiedo.user.auth.global.api.code.status.SuccessStatus;
import hobbiedo.user.auth.member.application.MemberService;
import hobbiedo.user.auth.member.converter.FindLoginIdConverter;
import hobbiedo.user.auth.member.converter.SignUpConverter;
import hobbiedo.user.auth.member.vo.request.FindLoginIdVO;
import hobbiedo.user.auth.member.vo.request.IntegrateSignUpVO;
import hobbiedo.user.auth.member.vo.response.SignUpVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/non-users")
@Tag(name = "Member", description = "사용자 서비스")
public class MemberController {
	private final MemberService memberService;
	private final EmailService idEmailService;

	public MemberController(MemberService memberService,
		@Qualifier(value = "idEmailService") EmailService idEmailService) {
		this.memberService = memberService;
		this.idEmailService = idEmailService;
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
}
