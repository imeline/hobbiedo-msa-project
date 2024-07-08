package hobbiedo.crew.kafka.dto;

import hobbiedo.crew.kafka.type.EntryExitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrewEntryExitDTO {
	private long crewId;
	private String uuid;
	private EntryExitType entryExitType;

	public static CrewEntryExitDTO toDto(long crewId, String uuid, EntryExitType entryExitType) {
		return CrewEntryExitDTO.builder()
			.crewId(crewId)
			.uuid(uuid)
			.entryExitType(entryExitType)
			.build();
	}
}
