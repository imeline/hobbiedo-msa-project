package hobbiedo.user.auth.email.application;

import org.springframework.stereotype.Service;

import hobbiedo.user.auth.email.domain.EmailCode;
import hobbiedo.user.auth.email.dto.request.EmailCheckDTO;
import hobbiedo.user.auth.email.infrastructure.EmailRepository;
import hobbiedo.user.auth.global.api.code.status.ErrorStatus;
import hobbiedo.user.auth.global.exception.MemberExceptionHandler;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailCheckService {
	private final EmailRepository emailRepository;

	public Boolean checkAuthCode(EmailCheckDTO emailCheckDTO) {
		EmailCode emailCode = emailRepository
			.findByEmail(emailCheckDTO.getEmail())
			.orElseThrow(() -> new MemberExceptionHandler(ErrorStatus.EMAIL_AUTH_NOT_MATCH));

		return emailCode.getAuthCode()
			.equals(emailCheckDTO.getAuthCode());
	}
}
