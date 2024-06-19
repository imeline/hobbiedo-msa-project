package hobbiedo.crew.kafka.dto;

import hobbiedo.crew.domain.Crew;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrewScoreDTO {
	private long crewId;

	public Crew toEntity(Crew crew, int changeScore) {
		return Crew.builder()
			.id(crew.getId())
			.regionId(crew.getRegionId())
			.hobbyId(crew.getHobbyId())
			.name(crew.getName())
			.introduction(crew.getIntroduction())
			.currentParticipant(crew.getCurrentParticipant())
			.joinType(crew.getJoinType())
			.profileUrl(crew.getProfileUrl())
			.score(crew.getScore() + changeScore)
			.active(crew.isActive())
			.build();
	}
}
