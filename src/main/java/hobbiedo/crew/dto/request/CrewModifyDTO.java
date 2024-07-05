package hobbiedo.crew.dto.request;

import java.util.List;

import hobbiedo.crew.domain.Crew;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CrewModifyDTO {

	@NotBlank
	private String profileUrl;

	@NotBlank
	private String introduction;

	private List<String> hashTagList;

	@NotNull
	private int joinType;

	public Crew toModifyCrewEntity(Crew crew) {
		return Crew.builder()
			.id(crew.getId())
			.regionId(crew.getRegionId())
			.hobbyId(crew.getHobbyId())
			.name(crew.getName())
			.introduction(introduction)
			.currentParticipant(crew.getCurrentParticipant())
			.joinType(joinType)
			.profileUrl(profileUrl)
			.score(crew.getScore())
			.active(crew.isActive())
			.build();
	}
}
