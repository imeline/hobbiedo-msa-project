package hobbiedo.crew.dto.response;

import hobbiedo.crew.domain.Crew;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CrewProfileDTO {
	private long crewId;
	private String name;
	private String profileUrl;

	public static CrewProfileDTO toDto(Crew crew) {
		return CrewProfileDTO.builder()
			.crewId(crew.getId())
			.name(crew.getName())
			.profileUrl(crew.getProfileUrl())
			.build();
	}
}
