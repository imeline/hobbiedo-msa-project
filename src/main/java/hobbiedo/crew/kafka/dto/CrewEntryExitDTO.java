package hobbiedo.crew.kafka.dto;

import java.util.List;

import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.domain.CrewMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrewEntryExitDTO {
	private long crewId;
	private String uuid;

	public CrewMember toCrewMemberEntity(boolean hostStatus) {
		return CrewMember.builder()
			.uuid(uuid)
			.hostStatus(hostStatus)
			.build();
	}

	public Crew toCrewEntity(List<CrewMember> crewMembers) {
		return Crew.builder()
			.crewId(crewId)
			.crewMembers(crewMembers)
			.build();
	}
}
