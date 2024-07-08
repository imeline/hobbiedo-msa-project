package hobbiedo.user.auth.member.dto.request;

import java.time.LocalDate;

import hobbiedo.user.auth.member.type.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegrateSignUpDTO {
	@Schema(description = "가입하는 사람 이름")
	private final String name;

	@Schema(description = "가입 이메일, 이메일 형식 검증함")
	private final String email;

	@Schema(description = "가입 휴대폰 번호, 10~11자리 '-' 제외")
	private final String phoneNumber;

	@Schema(description = "성별, FEMAIL, MAIL 중 하나 해야함")
	private final GenderType gender;

	@Schema(description = "가입 아이디, 8~20자리(영어+숫자), 특수문자 X")
	private final String loginId;

	@Schema(description = "가입 비밀번호, 8 ~ 20자리(영어+숫자+특수문자")
	private final String password;

	@Schema(description = "가입자 생년월일, YYYY-mm-dd 형식만 허용")
	private final LocalDate birth;
}
