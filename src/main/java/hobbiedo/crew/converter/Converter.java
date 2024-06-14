// package hobbiedo.crew.converter;
//
// import hobbiedo.crew.domain.Crew;
// import hobbiedo.crew.domain.CrewMember;
// import hobbiedo.crew.domain.HashTag;
// import hobbiedo.crew.dto.request.CrewDTO;
// import lombok.AccessLevel;
// import lombok.NoArgsConstructor;
//
// @NoArgsConstructor(access = AccessLevel.PRIVATE)
// public class Converter {
// 	public Crew toCrewEntity(CrewDTO crewDTO, int score, int currentParticipant) {
// 		return Crew.builder()
// 			.regionId(crewDTO.getRegionId())
// 			.hobbyId(crewDTO.getHobbyId())
// 			.name(crewDTO.getName())
// 			.introduction(crewDTO.getIntroduction())
// 			.currentParticipant(currentParticipant)
// 			.joinType(crewDTO.getJoinType())
// 			.profileUrl(crewDTO.getProfileUrl())
// 			.score(score)
// 			.build();
// 	}
//
// 	public CrewMember toCrewMemberEntity(Crew crew, String uuid, int role, ) {
// 		return CrewMember.builder()
// 			.crew(crew)
// 			.uuid(uuid)
// 			.role(1) // 방장
// 			.banned(false) // default false : 블랙리스트 아님
// 			.build();
// 	}
//
// 	public HashTag toHashTagEntity(Crew crew, String hashTagName) {
// 		return HashTag.builder()
// 			.crew(crew)
// 			.name(hashTagName)
// 			.build();
// 	}
//
// }
