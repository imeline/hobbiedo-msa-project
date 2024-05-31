package hobbiedo.user.auth.member.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "PatchPassword 요청 VO")
public class UpdatePasswordVO {
	@Schema(description = "기존 비밀번호")
	private String currentPassword;
	
	@Schema(description = "새로운 비밀번호")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
		message = "비밀번호는 8~20자리 사이의 영어+숫자+특수문자로 이뤄져야합니다.")
	private String newPassword;

}
