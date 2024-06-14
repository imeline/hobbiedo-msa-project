package hobbiedo.crew.dto.request;

import java.util.List;

import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.domain.CrewMember;
import hobbiedo.crew.domain.HashTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CrewRequestDTO {

	private String profileUrl;

	@NotNull
	private Long regionId;

	@NotNull
	private Long hobbyId;

	@NotBlank
	private String name;

	@NotBlank
	private String introduction;

	private List<String> hashTagList;

	@NotNull
	private int joinType;

	public Crew toCrewEntity() {
		return Crew.builder()
			.regionId(regionId)
			.hobbyId(hobbyId)
			.name(name)
			.introduction(introduction)
			.currentParticipant(1) // default 1 : 방장
			.joinType(joinType)
			.profileUrl(profileUrl == null ? "https://hobbiedo-bucket.s3.ap-northeast-2.amazonaws.com/image_1718327243910_crew.png" :
				profileUrl)
			.score(0) // default 0 : 초기 0점
			.active(true) // default true : 활성화
			.build();
	}

	public CrewMember toCrewMemberEntity(Crew crew, String uuid) {
		return CrewMember.builder()
			.crew(crew)
			.uuid(uuid)
			.role(1) // 방장
			.banned(false) // default false : 블랙리스트 아님
			.build();
	}

	public HashTag toHashTagEntity(Crew crew, String hashTagName) {
		return HashTag.builder()
			.crew(crew)
			.name(hashTagName)
			.build();
	}
}
