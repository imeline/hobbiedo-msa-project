package hobbiedo.user.auth.google.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.user.auth.global.api.ApiResponse;
import hobbiedo.user.auth.global.api.code.status.SuccessStatus;
import hobbiedo.user.auth.google.application.GoogleService;
import hobbiedo.user.auth.google.dto.request.GoogleLoginDTO;
import hobbiedo.user.auth.google.dto.request.GoogleSignUpDTO;
import hobbiedo.user.auth.member.vo.response.LoginResponseVO;
import hobbiedo.user.auth.member.vo.response.SignUpVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/non-users")
@Tag(name = "Auth", description = "사용자 인증 API")
public class GoogleController {

	private final GoogleService googleService;

	@Operation(summary = "구글 로그인",
		description = "통합 회원이 아니면 에러를 return, 구글 회원이 아니면 구글 회원가입 후 로그인, 구글 회원이면 로그인")
	@PostMapping("/login/google")
	public ApiResponse<LoginResponseVO> googleLogin(@RequestBody GoogleLoginDTO googleLoginDTO) {
		return ApiResponse.onSuccess(
			SuccessStatus.GOOGLE_LOGIN_SUCCESS.getMessage(),
			googleService.loginGoogle(googleLoginDTO));
	}

	@Operation(summary = "구글 회원가입", description = "통합과 구글 회원가입 후, uuid return")
	@PostMapping("/sign-up/google")
	public ApiResponse<SignUpVO> googleSignUp(@RequestBody GoogleSignUpDTO googleSignUpDTO) {
		return ApiResponse.onSuccess(
			SuccessStatus.GOOGLE_SIGN_UP_SUCCESS.getMessage(),
			googleService.signUpGoogle(googleSignUpDTO));
	}
}
