package hobbiedo.crew.dto.response;

import hobbiedo.crew.domain.Crew;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CrewIdNameDTO {
	private long crewId;
	private String name;

	public static CrewIdNameDTO toDto(Crew crew) {
		return CrewIdNameDTO.builder()
			.crewId(crew.getId())
			.name(crew.getName())
			.build();
	}
}
