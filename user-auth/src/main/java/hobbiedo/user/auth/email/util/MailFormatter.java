package hobbiedo.user.auth.email.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import hobbiedo.user.auth.email.type.MailType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailFormatter {
	private final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String senderEmail;

	public void sendMail(String receiver, MailType mailType, String message) {
		MimeMessage email = javaMailSender.createMimeMessage();

		String title = mailType.subject;
		String text = mailType.text.formatted(message);

		try {
			email.setFrom(senderEmail);
			email.setRecipients(MimeMessage.RecipientType.TO, receiver);
			email.setSubject(title);
			email.setText(text, "UTF-8", "html");
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		javaMailSender.send(email);
	}

}
