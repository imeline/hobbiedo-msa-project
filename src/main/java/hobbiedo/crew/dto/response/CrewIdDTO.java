package hobbiedo.crew.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CrewIdDTO {
	private Long crewId;

	public static CrewIdDTO toDto(Long crewId) {
		return CrewIdDTO.builder()
			.crewId(crewId)
			.build();
	}
}
