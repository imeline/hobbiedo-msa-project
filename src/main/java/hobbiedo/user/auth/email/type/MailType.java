package hobbiedo.user.auth.email.type;

public enum MailType {
	LOGIN_ID("취미한다 회원 아이디 발송",
		"""
			<h3> [취미한다] 회원 아이디 입니다. </h3>
			<h1> %s </h1>
			<h3> 서비스를 이용해주셔서 감사합니다!! </h3>
			""");
	public final String subject;
	public final String text;

	MailType(String subject, String text) {
		this.subject = subject;
		this.text = text;
	}
}
