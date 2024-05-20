package hobbiedo.user.auth.user.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.user.auth.global.api.ApiResponse;
import hobbiedo.user.auth.global.api.code.status.SuccessStatus;
import hobbiedo.user.auth.user.application.AuthService;
import hobbiedo.user.auth.user.converter.LoginConverter;
import hobbiedo.user.auth.user.vo.request.LoginRequestVO;
import hobbiedo.user.auth.user.vo.response.LoginResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
				authService.login(LoginConverter.toRequestDTO(loginVO))
		);
	}
}
