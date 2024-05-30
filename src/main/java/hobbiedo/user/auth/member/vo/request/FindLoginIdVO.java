package hobbiedo.user.auth.member.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "아이디 찾기 요청 VO")
public class FindLoginIdVO {
	@Schema(description = "회원 아이디")
	private String name;
	@Schema(description = "회원 이메일")
	private String email;
}
