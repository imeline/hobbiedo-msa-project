package hobbiedo.crew.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CrewNameDTO {
	private String name;

	public static CrewNameDTO toDto(String name) {
		return CrewNameDTO.builder()
			.name(name)
			.build();
	}
}
