package hobbiedo.crew.application;

import java.util.List;

import org.springframework.stereotype.Service;

import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.domain.CrewMember;
import hobbiedo.crew.infrastructure.ReplicaCrewRepository;
import hobbiedo.crew.kafka.dto.CrewEntryExitDTO;
import hobbiedo.global.api.code.status.ErrorStatus;
import hobbiedo.global.api.exception.handler.ReadOnlyExceptionHandler;
import hobbiedo.member.domain.MemberProfile;
import hobbiedo.member.infrastructure.ReplicaMemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplicaCrewServiceImp implements ReplicaCrewService {

	private final ReplicaCrewRepository replicaCrewRepository;
	private final ReplicaMemberRepository replicaMemberRepository;

	@Override
	public void createCrew(CrewEntryExitDTO crewEntryExitDTO) {
		MemberProfile memberProfile = getMemberProfile(crewEntryExitDTO.getUuid());
		CrewMember crewMember = crewEntryExitDTO.toCrewMemberEntity(memberProfile,
			true); // crew 생성 때 소모임장 추가
		replicaCrewRepository.save(crewEntryExitDTO.toCrewEntity(List.of(crewMember)));
	}

	@Override
	public void addCrewMember(CrewEntryExitDTO crewEntryExitDTO) {
		MemberProfile memberProfile = getMemberProfile(crewEntryExitDTO.getUuid());
		CrewMember crewMember = crewEntryExitDTO.toCrewMemberEntity(memberProfile,
			false); // crew 가입 때 소모임원 추가
		Crew crew = getCrew(crewEntryExitDTO.getCrewId());
		crew.getCrewMembers().add(crewMember);
		replicaCrewRepository.save(crew);
	}

	@Override
	public void deleteCrewMember(CrewEntryExitDTO crewEntryExitDTO) {
		Crew crew = getCrew(crewEntryExitDTO.getCrewId());
		crew.getCrewMembers()
			.removeIf(crewMember -> crewMember.getUuid().equals(crewEntryExitDTO.getUuid()));
		replicaCrewRepository.save(crew);
	}

	private MemberProfile getMemberProfile(String uuid) {
		return replicaMemberRepository.findByUuid(uuid)
			.orElseThrow(
				() -> new ReadOnlyExceptionHandler(ErrorStatus.NOT_FOUND_MEMBER_PROFILE));
	}

	private Crew getCrew(long crewId) {
		return replicaCrewRepository.findByCrewId(crewId)
			.orElseThrow(() -> new ReadOnlyExceptionHandler(ErrorStatus.NOT_FOUND_CREW));
	}
}
