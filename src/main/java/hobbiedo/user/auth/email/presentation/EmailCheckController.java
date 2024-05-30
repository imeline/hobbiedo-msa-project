package hobbiedo.user.auth.email.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.user.auth.email.application.EmailCheckService;
import hobbiedo.user.auth.email.converter.EmailCheckConverter;
import hobbiedo.user.auth.email.vo.request.EmailCheckVO;
import hobbiedo.user.auth.global.api.ApiResponse;
import hobbiedo.user.auth.global.api.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/non-users")
@RequiredArgsConstructor
@Tag(name = "Email", description = "이메일 관련 서비스입니다.")
public class EmailCheckController {
	private final EmailCheckService emailCheckService;

	@PostMapping("/email/check")
	@Operation(summary = "이메일 인증 코드 일치 확인",
		description = "인증 코드가 일치하는지 확인합니다.")
	public ApiResponse<Boolean> checkEmailCode(@RequestBody EmailCheckVO emailCheckVO) {
		return ApiResponse.onSuccess(
			SuccessStatus.EMAIL_AUTH_MATCH,
			emailCheckService.checkAuthCode(EmailCheckConverter.toDTO(emailCheckVO))
		);
	}
}
