package hobbiedo.crew.application;

import org.springframework.stereotype.Service;

import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.dto.request.CrewRequestDTO;
import hobbiedo.crew.infrastructure.jpa.CrewMemberRepository;
import hobbiedo.global.exception.GlobalException;
import hobbiedo.global.status.ErrorStatus;
import hobbiedo.region.infrastructure.RegionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidationService {
	private final CrewMemberRepository crewMemberRepository;
	private final RegionRepository regionRepository;

	public void isValidCrew(CrewRequestDTO crewDTO, String uuid) {

		// HashTag 개수 체크
		if (crewDTO.getHashTagList().size() > 5) {
			throw new GlobalException(ErrorStatus.INVALID_HASH_TAG_COUNT);
		}
		// joinType 체크
		if (crewDTO.getJoinType() != 0 && crewDTO.getJoinType() != 1) {
			throw new GlobalException(ErrorStatus.INVALID_JOIN_TYPE);
		}

		// 활동 지역이 존재하는지 체크
		if (!regionRepository.existsById(crewDTO.getRegionId())) {
			throw new GlobalException(ErrorStatus.NO_EXIST_REGION);
		}
	}

	public void isValidFullCrew(Crew crew) {
		// 가입인원 다 찾는지 확인
		if (crew.getCurrentParticipant() >= 100) {
			throw new GlobalException(ErrorStatus.INVALID_MAX_PARTICIPANT);
		}
	}

	public void isValidCrewMember(Crew crew, String uuid) {
		// 블랙리스트인지 확인
		if (crewMemberRepository.existsByCrewIdAndUuidAndRole(crew.getId(), uuid, 2)) {
			throw new GlobalException(ErrorStatus.INVALID_BANNED);
		}
		// 이미 가입한 방인지 체크
		if (crewMemberRepository.existsByCrewIdAndUuid(crew.getId(), uuid)) {
			throw new GlobalException(ErrorStatus.ALREADY_JOINED_CREW);
		}
	}

	public void isValidHost(Long crewId, String uuid) {
		if (crewMemberRepository.findByCrewIdAndRole(crewId, 1)
			.map(crewMember -> !crewMember.getUuid().equals(uuid))
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW_ID_OR_HOST))) {
			throw new GlobalException(ErrorStatus.INVALID_HOST_ACCESS);
		}
	}
}
