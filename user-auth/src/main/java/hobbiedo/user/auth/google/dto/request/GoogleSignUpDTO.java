package hobbiedo.user.auth.google.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import hobbiedo.user.auth.member.domain.IntegrateAuth;
import hobbiedo.user.auth.member.domain.Member;
import hobbiedo.user.auth.member.type.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "회원가입 요청 VO")
public class GoogleSignUpDTO {

	@NotBlank
	@Schema(description = "구글 로그인 제공 ID")
	private String externalId;

	@Schema(description = "가입하는 사람 이름")
	@Pattern(regexp = "^[가-힣a-zA-Z]{2,}$", message = "문자는 최소 2글자 이상의 한글과 영어만 가능합니다.")
	private String name;

	@Schema(description = "가입 이메일, 이메일 형식 검증함")
	@Email(message = "이메일 형식이 일치하지 않습니다.")
	private String email;

	@Schema(description = "가입 휴대폰 번호, 10~11자리 '-' 제외")
	@Pattern(regexp = "^\\d{10,11}$", message = "휴대폰 번호 형식이 일치하지 않습니다.")
	private String phoneNumber;

	@Schema(description = "성별, FEMALE, MALE 중 하나 해야함")
	private GenderType gender;

	@Schema(description = "가입 아이디, 8~20자리(영어+숫자), 특수문자 X")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$",
		message = "아이디는 8~20자리의 영어+숫자로만 이뤄져야합니다.(특수 문자x)")
	private String loginId;

	@Schema(description = "가입 비밀번호, 8 ~ 20자리(영어+숫자+특수문자")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
		message = "비밀번호는 8~20자리 사이의 영어+숫자+특수문자로 이뤄져야합니다.")
	private String password;

	@Schema(description = "가입자 생년월일, YYYY-mm-dd 형식만 허용")
	private LocalDate birth;

	public Member toMemberEntity() {
		return Member.builder()
			.name(name)
			.uuid(UUID.randomUUID().toString())
			.email(email)
			.phoneNumber(phoneNumber)
			.gender(gender)
			.birth(birth)
			.build();
	}

	public IntegrateAuth toIntegrateAuthEntity(Member member, String passwordEncoder) {
		return IntegrateAuth.builder()
			.member(member)
			.loginId(loginId)
			.password(passwordEncoder)
			.build();
	}
}
