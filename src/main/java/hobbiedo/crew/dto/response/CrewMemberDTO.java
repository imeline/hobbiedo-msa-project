package hobbiedo.crew.dto.response;

import java.util.List;

import hobbiedo.crew.domain.CrewMember;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CrewMemberDTO {
	private String uuid;
	private String name;
	private String profileUrl;
	private boolean hostStatus;

	public static List<CrewMemberDTO> toDtoList(List<CrewMember> crewMembers) {
		return crewMembers.stream()
			.map(crewMember -> CrewMemberDTO.builder()
				.uuid(crewMember.getUuid())
				.name(crewMember.getName())
				.profileUrl(crewMember.getProfileUrl())
				.hostStatus(crewMember.isHostStatus())
				.build())
			.toList();
	}
}
