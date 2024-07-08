package hobbiedo.crew.dto.response;

import java.time.LocalDateTime;

import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.domain.JoinForm;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyJoinFormDTO {
	private String joinFormId;
	private String crewName;
	private String crewUrl;
	private String joinMessage;
	private LocalDateTime createdAt;

	public static MyJoinFormDTO toDto(JoinForm joinForm, Crew crew) {
		return MyJoinFormDTO.builder()
			.joinFormId(joinForm.getId())
			.crewName(crew.getName())
			.crewUrl(crew.getProfileUrl())
			.joinMessage(joinForm.getJoinMessage())
			.createdAt(joinForm.getCreatedAt())
			.build();
	}
}
