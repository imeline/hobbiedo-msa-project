package hobbiedo.crew.dto.response;

import hobbiedo.crew.domain.JoinForm;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinFormListDTO {
	private String joinFormId;
	private String name;
	private String profileUrl;
	private String joinMessage;

	public static JoinFormListDTO toDto(JoinForm joinForm) {
		return JoinFormListDTO.builder()
			.joinFormId(joinForm.getId())
			.name(joinForm.getName())
			.profileUrl(joinForm.getProfileUrl())
			.joinMessage(joinForm.getJoinMessage())
			.build();
	}
}
