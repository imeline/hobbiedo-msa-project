package hobbiedo.user.auth.user.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.user.auth.global.api.ApiResponse;
import hobbiedo.user.auth.global.api.code.status.SuccessStatus;
import hobbiedo.user.auth.user.application.AuthService;
import hobbiedo.user.auth.user.converter.AuthConverter;
import hobbiedo.user.auth.user.vo.request.LoginRequestVO;
import hobbiedo.user.auth.user.vo.request.ReIssueRequestVO;
import hobbiedo.user.auth.user.vo.response.LoginResponseVO;
import hobbiedo.user.auth.user.vo.response.ReIssueResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Tag(name = "Auth", description = "사용자 인증 API")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	@Operation(summary = "사용자 로그인",
			description = "특정 사용자가 로그인하여 Access와 Refresh 토큰을 발급합니다.")
	public ApiResponse<LoginResponseVO> loginApi(@RequestBody LoginRequestVO loginVO) {
		return ApiResponse.onSuccess(
				SuccessStatus.USER_INTEGRATED_LOGIN_SUCCESS.getMessage(),
				authService.login(AuthConverter.toRequestDTO(loginVO))
		);
	}

	@PostMapping("/re-issue")
	@Operation(summary = "Access Token 재발급",
			description = "Refresh Token이 만료되지 않았다면, Access Token과 Refresh Token을 재발급합니다.")
	public ApiResponse<ReIssueResponseVO> reIssueApi(@RequestBody @Valid ReIssueRequestVO reIssueVO) {
		return ApiResponse.onSuccess(
				SuccessStatus.REISSUE_TOKEN_SUCCESS.getMessage(),
				authService.reIssueToken(AuthConverter.toRequestDTO(reIssueVO))
		);
	}
}
