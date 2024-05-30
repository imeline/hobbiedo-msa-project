package hobbiedo.user.auth.email.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.user.auth.email.type.MailType;
import hobbiedo.user.auth.email.util.MailFormatter;
import hobbiedo.user.auth.member.dto.response.ResetPasswordDTO;
import lombok.RequiredArgsConstructor;

@Service(value = "passwordEmailService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PasswordEmailService implements EmailService {

	private final MailFormatter mailFormatter;

	@Override
	@Transactional
	public void sendMail(Object object) {
		hobbiedo.user.auth.member.dto.response.ResetPasswordDTO resetPasswordDTO = getDTO(object);
		send(resetPasswordDTO);
	}

	private hobbiedo.user.auth.member.dto.response.ResetPasswordDTO getDTO(Object object) {
		hobbiedo.user.auth.member.dto.response.ResetPasswordDTO resetPasswordDTO = null;
		if (object instanceof hobbiedo.user.auth.member.dto.response.ResetPasswordDTO) {
			resetPasswordDTO = (ResetPasswordDTO)object;
		}
		return resetPasswordDTO;
	}

	private void send(hobbiedo.user.auth.member.dto.response.ResetPasswordDTO resetPasswordDTO) {
		mailFormatter.sendMail(
			resetPasswordDTO.getEmail(),
			MailType.TEMP_PASSWORD,
			resetPasswordDTO.getTempPassword());
	}
}
