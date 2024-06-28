package hobbiedo.crew.application;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.domain.CrewMember;
import hobbiedo.crew.dto.response.CrewMemberDTO;
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
	public List<CrewMemberDTO> getCrewMembers(long crewId, String uuid) {
		Crew crew = getCrew(crewId);
		return crew.getCrewMembers().stream()
			.map(crewMember -> CrewMemberDTO.toDto(crewMember, determineRole(crewMember, uuid)))
			.sorted(Comparator.comparing(CrewMemberDTO::getRole).reversed())
			.toList();
	}

	private int determineRole(CrewMember crewMember, String currentUuid) {
		if (crewMember.getUuid().equals(currentUuid)) {
			return crewMember.isHostStatus() ? 3 : 2;
		} else {
			return crewMember.isHostStatus() ? 1 : 0;
		}
	}

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

	/**
	 * 소모임장 여부 확인
	 * @param crewId
	 * @param writerUuid
	 * @return
	 */
	@Override
	public boolean isHost(Long crewId, String writerUuid) {

		Crew crew = getCrew(crewId);

		// 소모임원 중 소모임장인지 확인
		return crew.getCrewMembers().stream()
			.anyMatch(
				crewMember -> crewMember.getUuid().equals(writerUuid) && crewMember.isHostStatus());
	}
}
