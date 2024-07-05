package hobbiedo.member.kafka.dto;

import hobbiedo.member.domain.MemberProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyProfileDTO {
	private String uuid;
	private String profileUrl;

	public MemberProfile toEntity(MemberProfile memberProfile) {
		return MemberProfile.builder()
			.id(memberProfile.getId())
			.uuid(memberProfile.getUuid())
			.name(memberProfile.getName())
			.profileUrl(profileUrl)
			.build();
	}
}
