package hobbiedo.user.auth.google.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoogleLoginDTO {

	@Schema(description = "구글 로그인 제공 ID")
	@NotBlank
	private String externalId;

	@Schema(description = "가입하는 사람 이름")
	@NotBlank
	private String name;

	@Schema(description = "가입 이메일, 이메일 형식 검증함")
	@Email(message = "이메일 형식이 일치하지 않습니다.")
	private String email;
}
