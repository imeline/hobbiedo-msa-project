package hobbiedo.crew.dto.request;

import hobbiedo.crew.domain.CrewMember;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CrewOutDTO {

	@NotBlank
	private String outUuid;

	public CrewMember toCrewMemberEntity(CrewMember crewMember) {
		return CrewMember.builder()
			.id(crewMember.getId())
			.crew(crewMember.getCrew())
			.uuid(crewMember.getUuid())
			.role(3) // 블랙리스트
			.build();
	}
}
