package hobbiedo.crew.dto.response;

import java.util.List;

import hobbiedo.crew.domain.Crew;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CrewDetailDTO {
	private long crewId;
	private String crewName;
	private String addressName;
	private String introduction;
	private int currentParticipant;
	private int joinType;
	private String profileUrl;
	private List<String> hashTagList;

	public static CrewDetailDTO toDto(Crew crew, String addressName, List<String> hashTagList) {
		return CrewDetailDTO.builder()
			.crewId(crew.getId())
			.crewName(crew.getName())
			.addressName(addressName)
			.introduction(crew.getIntroduction())
			.currentParticipant(crew.getCurrentParticipant())
			.joinType(crew.getJoinType())
			.profileUrl(crew.getProfileUrl())
			.hashTagList(hashTagList)
			.build();
	}
}
