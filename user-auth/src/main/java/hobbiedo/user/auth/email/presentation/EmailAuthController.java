package hobbiedo.user.auth.email.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.user.auth.email.application.EmailService;
import hobbiedo.user.auth.email.converter.EmailAuthConverter;
import hobbiedo.user.auth.email.vo.request.EmailAuthVO;
import hobbiedo.user.auth.global.api.ApiResponse;
import hobbiedo.user.auth.global.api.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("v1/non-users")
@Tag(name = "Email", description = "이메일 관련 서비스입니다.")
public class EmailAuthController {
	private final EmailService emailService;

	@Autowired
	public EmailAuthController(
		@Qualifier("codeEmailService") EmailService emailService) {
		this.emailService = emailService;
	}

	@PostMapping("/email/auth")
	@Operation(summary = "이메일 인증 코드 전송",
		description = "이메일 인증 코드를 해당 이메일로 전송합니다.")
	public ApiResponse<Void> sendEmailAuthApi(@RequestBody EmailAuthVO emailAuthVO) {
		emailService.sendMail(EmailAuthConverter.toDTO(emailAuthVO));
		return ApiResponse.onSuccess(
			SuccessStatus.SEND_AUTH_MAIL_SUCCESS,
			null
		);
	}
}
