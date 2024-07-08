package hobbiedo.crew.application;

import java.util.List;

import org.springframework.stereotype.Service;

import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.infrastructure.CrewMemberRepository;
import hobbiedo.global.exception.GlobalException;
import hobbiedo.global.status.ErrorStatus;
import hobbiedo.region.infrastructure.RegionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidationService {
	private final CrewMemberRepository crewMemberRepository;
	private final RegionRepository regionRepository;

	public void isValidRegionIdCountHostCrew(long regionId, String uuid) {
		// 활동 지역이 존재하는지 체크
		if (!regionRepository.existsById(regionId)) {
			throw new GlobalException(ErrorStatus.NO_EXIST_REGION);
		}
		// 이미 만든 방이 5개 이상인지 체크
		if (crewMemberRepository.countByUuidAndRole(uuid, 1) >= 5) {
			throw new GlobalException(ErrorStatus.INVALID_MAX_HOST_COUNT);
		}
	}

	public void isValidHashTagJoinType(List<String> hashTagList, int joinType) {
		// HashTag 개수 체크
		if (hashTagList.size() > 5) {
			throw new GlobalException(ErrorStatus.INVALID_HASH_TAG_COUNT);
		}
		// joinType 체크
		if (joinType != 0 && joinType != 1) {
			throw new GlobalException(ErrorStatus.INVALID_JOIN_TYPE);
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
