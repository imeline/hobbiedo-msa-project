package hobbiedo.crew.dto.response;

import java.util.List;

import hobbiedo.crew.domain.Crew;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CrewResponseDTO {

	private String profileUrl;

	private String name;

	private String introduction;

	private List<String> hashTagList;

	private int joinType;

	public static CrewResponseDTO toDto(Crew crew, List<String> hashTagList) {
		return CrewResponseDTO.builder()
			.profileUrl(crew.getProfileUrl())
			.name(crew.getName())
			.introduction(crew.getIntroduction())
			.joinType(crew.getJoinType())
			.hashTagList(hashTagList)
			.build();
	}

}
