package hobbiedo.user.auth.member.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "아이디 중복 검사 반환값(VO)")
public class CheckLoginIdVO {
	@Schema(description = "사용 가능 유무")
	@JsonProperty("isPossible")
	private Boolean isPossible;
}
