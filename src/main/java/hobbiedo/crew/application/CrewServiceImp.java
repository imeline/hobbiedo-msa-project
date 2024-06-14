package hobbiedo.crew.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.chat.application.ChatService;
import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.domain.CrewMember;
import hobbiedo.crew.dto.request.CrewRequestDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;
import hobbiedo.crew.dto.response.CrewResponseDTO;
import hobbiedo.crew.infrastructure.CrewMemberRepository;
import hobbiedo.crew.infrastructure.CrewRepository;
import hobbiedo.crew.infrastructure.HashTagRepository;
import hobbiedo.global.exception.GlobalException;
import hobbiedo.global.status.ErrorStatus;
import hobbiedo.region.infrastructure.RegionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewServiceImp implements CrewService {

	private final CrewRepository crewRepository;
	private final CrewMemberRepository crewMemberRepository;
	private final HashTagRepository hashTagRepository;
	private final ChatService chatService;
	private final RegionRepository regionRepository;

	@Transactional
	@Override
	public CrewIdDTO createCrew(CrewRequestDTO crewDTO, String uuid) {
		isVaildCreateCrew(crewDTO, uuid);
		// Crew 생성
		Crew crew = crewRepository.save(crewDTO.toCrewEntity());
		// 방장 CrewMember 생성
		crewMemberRepository.save(crewDTO.toCrewMemberEntity(crew, uuid));
		// HashTag 생성
		if (!crewDTO.getHashTagList().isEmpty()) {
			crewDTO.getHashTagList().forEach(hashTagName -> hashTagRepository.save(
				crewDTO.toHashTagEntity(crew, hashTagName)));
		}
		// ChatLastStatus 생성
		chatService.createChatStatus(crew.getId(), uuid);

		return CrewIdDTO.toDto(crew.getId());
	}

	private void isVaildCreateCrew(CrewRequestDTO crewDTO, String uuid) {
		// HashTag 개수 체크
		if (crewDTO.getHashTagList().size() > 5) {
			throw new GlobalException(ErrorStatus.INVALID_HASH_TAG_COUNT);
		}
		// joinType 체크
		if (crewDTO.getJoinType() != 0 && crewDTO.getJoinType() != 1) {
			throw new GlobalException(ErrorStatus.INVALID_JOIN_TYPE);
		}
		// 이미 만든 방이 5개 이상인지 체크
		if (crewMemberRepository.countByUuidAndRole(uuid, 1) >= 5) {
			throw new GlobalException(ErrorStatus.INVALID_MAX_HOST_COUNT);
		}
	}

	@Transactional
	@Override
	public void joinCrew(Long crewId, String uuid) {
		Crew crew = crewRepository.findById(crewId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));
		isVaildJoinCrew(crew, uuid);
		// CrewMember 생성
		crewMemberRepository.save(CrewMember.builder()
			.crew(crew)
			.uuid(uuid)
			.role(0) // 일반회원
			.banned(false) // default false : 블랙리스트 아님
			.build());
		// 참여인원 증가
		crewRepository.save(Crew.builder()
			.id(crew.getId())
			.regionId(crew.getRegionId())
			.hobbyId(crew.getHobbyId())
			.name(crew.getName())
			.introduction(crew.getIntroduction())
			.currentParticipant(crew.getCurrentParticipant() + 1)
			.joinType(crew.getJoinType())
			.profileUrl(crew.getProfileUrl())
			.score(crew.getScore())
			.active(crew.isActive())
			.build());
	}

	private void isVaildJoinCrew(Crew crew, String uuid) {
		// 블랙리스트인지 확인
		if (crewMemberRepository.existsByCrewIdAndUuidAndBanned(crew.getId(), uuid, true)) {
			throw new GlobalException(ErrorStatus.INVALID_BANNED);
		}
		// 이미 가입한 방인지 체크
		if (crewMemberRepository.existsByCrewIdAndUuid(crew.getId(), uuid)) {
			throw new GlobalException(ErrorStatus.ALREADY_JOINED_CREW);
		}
		// joinType 체크
		if (crew.getJoinType() == 1) {
			throw new GlobalException(ErrorStatus.INVALID_JOIN_TYPE);
		}
		// 가입인원 다 찾는지 확인
		if (crew.getCurrentParticipant() >= 100) {
			throw new GlobalException(ErrorStatus.INVALID_MAX_PARTICIPANT);
		}
	}

	@Override
	public CrewResponseDTO getCrewInfo(Long crewId) {
		Crew crew = crewRepository.findById(crewId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));

		List<String> hashTagList = hashTagRepository.findNamesByCrewId(crewId);
		String addressName = regionRepository.findAddressNameById(crew.getRegionId())
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_REGION));

		return CrewResponseDTO.toDto(crew, addressName, hashTagList);
	}

	@Override
	public List<CrewIdDTO> getCrewsByHobbyAndRegion(long hobbyId, long regionId) {
		List<CrewIdDTO> crewIds = new ArrayList<>( // 아래에서 랜덤하게 섞기 때문에 가변 객체로
			crewRepository.findIdsByHobbyAndRegion(hobbyId, regionId)
				.stream()
				.map(CrewIdDTO::toDto)
				.toList());

		Collections.shuffle(crewIds);

		return crewIds;
	}
}
