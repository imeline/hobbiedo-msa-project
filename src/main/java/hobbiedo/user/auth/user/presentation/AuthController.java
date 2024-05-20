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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ApiResponse<LoginResponseVO> loginApi(@RequestBody LoginRequestVO loginVO) {
		return ApiResponse.onSuccess(
				SuccessStatus.USER_INTEGRATED_LOGIN_SUCCESS.getMessage(),
				authService.login(LoginConverter.toRequestDTO(loginVO))
		);
	}
}
