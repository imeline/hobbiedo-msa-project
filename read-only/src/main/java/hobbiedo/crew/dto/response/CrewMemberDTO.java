package hobbiedo.crew.dto.response;

import hobbiedo.crew.domain.CrewMember;
import hobbiedo.member.domain.MemberProfile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CrewMemberDTO {
	private String uuid;
	private String name;
	private String profileUrl;
	private int role; // 0:일반 소모임원, 1:소모임장, 2:나, 3:나 & 소모임장

	public static CrewMemberDTO toDto(CrewMember crewMember, MemberProfile memberProfile, int role) {
		return CrewMemberDTO.builder()
			.uuid(crewMember.getUuid())
			.name(memberProfile.getName())
			.profileUrl(memberProfile.getProfileUrl())
			.role(role)
			.build();
	}
}
