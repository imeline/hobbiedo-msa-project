package hobbiedo.crew.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.domain.CrewMember;
import hobbiedo.crew.domain.HashTag;
import hobbiedo.crew.dto.request.CrewModifyDTO;
import hobbiedo.crew.dto.request.CrewOutDTO;
import hobbiedo.crew.dto.request.CrewRequestDTO;
import hobbiedo.crew.dto.response.CrewDetailDTO;
import hobbiedo.crew.dto.response.CrewHomeDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;
import hobbiedo.crew.dto.response.CrewIdNameDTO;
import hobbiedo.crew.dto.response.CrewNameDTO;
import hobbiedo.crew.dto.response.CrewProfileDTO;
import hobbiedo.crew.dto.response.CrewResponseDTO;
import hobbiedo.crew.infrastructure.CrewMemberRepository;
import hobbiedo.crew.infrastructure.CrewRepository;
import hobbiedo.crew.infrastructure.HashTagRepository;
import hobbiedo.crew.kafka.application.KafkaProducerService;
import hobbiedo.crew.kafka.dto.CrewEntryExitDTO;
import hobbiedo.crew.kafka.dto.CrewScoreDTO;
import hobbiedo.crew.kafka.type.EntryExitType;
import hobbiedo.global.exception.GlobalException;
import hobbiedo.global.status.ErrorStatus;
import hobbiedo.notification.application.NotificationService;
import hobbiedo.notification.type.NotificationType;
import hobbiedo.region.application.RegionService;
import hobbiedo.region.domain.Region;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewServiceImp implements CrewService {

	private final CrewRepository crewRepository;
	private final CrewMemberRepository crewMemberRepository;
	private final HashTagRepository hashTagRepository;
	private final RegionService regionService;
	private final ValidationService validationService;
	private final KafkaProducerService kafkaProducerService;
	private final NotificationService notificationService;

	@Transactional
	@Override
	public CrewIdDTO createCrew(CrewRequestDTO crewDTO, String uuid) {
		validationService.isValidRegionIdCountHostCrew(crewDTO.getRegionId(), uuid);
		validationService.isValidHashTagJoinType(crewDTO.getHashTagList(), crewDTO.getJoinType());
		// Crew 생성
		Crew crew = crewRepository.save(crewDTO.toCrewEntity());
		// 방장 CrewMember 생성
		crewMemberRepository.save(crewDTO.toCrewMemberEntity(crew, uuid));
		// HashTag 생성
		createHashTag(crew, crewDTO.getHashTagList());
		// ChatLastStatus 생성 및 입장 chat 전송
		kafkaProducerService.setCreateCrewTopic(
			CrewEntryExitDTO.toDto(crew.getId(), uuid, EntryExitType.ENTRY));

		return CrewIdDTO.toDto(crew.getId());
	}

	@Transactional
	protected void createHashTag(Crew crew, List<String> hashTagList) {
		if (!hashTagList.isEmpty()) {
			hashTagList.forEach(hashTagName -> hashTagRepository.save(
				HashTag.builder()
					.crew(crew)
					.name(hashTagName)
					.build()));
		}
	}

	@Transactional
	@Override
	public void joinCrew(Long crewId, String uuid) {
		Crew crew = getCrewById(crewId);
		validationService.isValidCrewMember(crew, uuid);
		joinCrewMember(crew, uuid);
	}

	@Transactional
	@Override
	public void joinCrewMember(Crew crew, String uuid) {
		validationService.isValidFullCrew(crew);
		// CrewMember 생성
		crewMemberRepository.save(CrewMember.builder()
			.crew(crew)
			.uuid(uuid)
			.role(0) // 일반회원
			.build());
		// 참여인원 증가
		changeCrewParticipant(crew, 1);
		// ChatLastStatus 생성 맟 입장 chat 전송
		kafkaProducerService.setJoinCrewTopic(
			CrewEntryExitDTO.toDto(crew.getId(), uuid, EntryExitType.ENTRY));
	}

	@Transactional
	protected void changeCrewParticipant(Crew crew, int changeCount) {
		crewRepository.save(Crew.builder()
			.id(crew.getId())
			.regionId(crew.getRegionId())
			.hobbyId(crew.getHobbyId())
			.name(crew.getName())
			.introduction(crew.getIntroduction())
			.currentParticipant(crew.getCurrentParticipant() + changeCount)
			.joinType(crew.getJoinType())
			.profileUrl(crew.getProfileUrl())
			.score(crew.getScore())
			.active(crew.isActive())
			.build());
	}

	@Override
	public List<CrewDetailDTO> getCrewInfoList(long hobbyId, long regionId, String uuid) {
		List<Crew> crews = getCrewsByHobbyAndRegion(hobbyId, regionId, uuid);
		List<CrewDetailDTO> crewDetailDTOList = new ArrayList<>(crews.stream()
			.map(crew -> {
				List<String> hashTagList = getHashTagList(crew.getId());
				String addressName = regionService.getAddressNameById(crew.getRegionId());
				return CrewDetailDTO.toDto(crew, addressName, hashTagList);
			})
			.toList());

		Collections.shuffle(crewDetailDTOList);
		return crewDetailDTOList;
	}

	private List<String> getHashTagList(long crewId) {
		return hashTagRepository.findNamesByCrewId(crewId);
	}

	private List<Crew> getCrewsByHobbyAndRegion(long hobbyId, long regionId, String uuid) {
		Region region = regionService.getRegionById(regionId);
		List<Long> joinedCrewIds = crewMemberRepository.findCrewIdsByUuid(uuid);

		List<Crew> crews = crewRepository.findAllByHobbyId(hobbyId).stream()
			// 사용자가 이미 가입한 크루 제외
			.filter(crew -> !joinedCrewIds.contains(crew.getId()))
			.toList();

		return crews.stream()
			.filter(crew -> {
				Region crewRegion = regionService.getRegionById(crew.getRegionId());
				return calculateDistance(region.getLatitude(), region.getLongitude(),
					crewRegion.getLatitude(), crewRegion.getLongitude())
					<= region.getCurrentSelectedRange();
			})
			.toList();
	}

	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		final int R = 6371; // 지구 반지름(km)

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);

		double haversinePart = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
			+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
			* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

		double count = 2 * Math.atan2(Math.sqrt(haversinePart), Math.sqrt(1 - haversinePart));

		return R * count; // km 단위
	}

	@Override
	public List<CrewProfileDTO> getCrewProfiles(String uuid) {
		List<CrewMember> crewMembers = crewMemberRepository.findByUuidAndRole(uuid);
		if (crewMembers.isEmpty()) {
			throw new GlobalException(ErrorStatus.NO_EXIST_CREW);
		}
		return crewMembers.stream()
			.map(crewMember -> CrewProfileDTO.toDto(crewMember.getCrew()))
			.toList();
	}

	@Override
	public CrewNameDTO getCrewName(Long crewId) {
		Crew crew = getCrewById(crewId);
		return CrewNameDTO.toDto(crew.getName());
	}

	@Transactional
	@Override
	public void exitCrew(Long crewId, String uuid) {
		if (crewMemberRepository.findByCrewIdAndRole(crewId, 1)
			.map(crewMember -> crewMember.getUuid().equals(uuid))
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW_ID_OR_HOST))) {
			throw new GlobalException(ErrorStatus.INVALID_HOST_WITHDRAWAL); // 방장은 탈퇴 불가
		}
		CrewMember crewMember = crewMemberRepository.findByCrewIdAndUuid(crewId, uuid)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW_MEMBER));
		crewMemberRepository.delete(crewMember);
		// 참여 인원 감소
		changeCrewParticipant(crewMember.getCrew(), -1);
		// 퇴장 chat 전송
		kafkaProducerService.setExitCrewTopic(
			CrewEntryExitDTO.toDto(crewId, uuid, EntryExitType.EXIT));
	}

	@Override
	public CrewResponseDTO getCrew(Long crewId) {
		Crew crew = getCrewById(crewId);
		List<String> hashTagList = getHashTagList(crewId);
		return CrewResponseDTO.toDto(crew, hashTagList);
	}

	@Transactional
	@Override
	public void modifyCrew(CrewModifyDTO crewModifyDTO, Long crewId, String uuid) {
		validationService.isValidHost(crewId, uuid);
		validationService.isValidHashTagJoinType(crewModifyDTO.getHashTagList(),
			crewModifyDTO.getJoinType());
		Crew crew = getCrewById(crewId);
		// Crew 수정
		crewRepository.save(crewModifyDTO.toModifyCrewEntity(crew));
		// HashTag 삭제
		hashTagRepository.deleteByCrewId(crewId);
		// HashTag 생성
		createHashTag(crew, crewModifyDTO.getHashTagList());
	}

	@Transactional
	@Override
	public void forcedExitCrew(CrewOutDTO crewOutDTO, Long crewId, String uuid) {
		validationService.isValidHost(crewId, uuid);
		// 블랙리스트로 변경
		CrewMember crewMember = crewMemberRepository.findByCrewIdAndUuid(crewId,
				crewOutDTO.getOutUuid())
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW_MEMBER));
		crewMemberRepository.save(crewOutDTO.toCrewMemberEntity(crewMember));
		// 참여 인원 감소
		changeCrewParticipant(crewMember.getCrew(), -1);
		// 강제 퇴장 chat 전송
		kafkaProducerService.setForceExitCrewTopic(
			CrewEntryExitDTO.toDto(crewId, crewOutDTO.getOutUuid(), EntryExitType.FORCE_EXIT));

		String notificationContent =
			crewMember.getCrew().getName() + NotificationType.FORCED_EXIT_CREW.getContent();
		notificationService.sendNotification(crewOutDTO.getOutUuid(), notificationContent,
			crewMember.getCrew().getProfileUrl());
	}

	@Override
	public List<CrewHomeDTO> getTop5LatestCrews(long hobbyId, long regionId, String uuid) {
		List<Crew> crews = getCrewsByHobbyAndRegion(hobbyId, regionId, uuid);
		return crews.stream()
			.sorted(Comparator.comparing(Crew::getCreatedAt).reversed())
			.limit(5)
			.map(crew -> {
				List<String> hashTagList = getHashTagList(crew.getId());
				String addressName = regionService.getAddressNameById(crew.getRegionId());
				return CrewHomeDTO.toDto(crew, addressName, hashTagList);
			})
			.toList();
	}

	@Override
	public List<CrewIdNameDTO> getTop5ScoreCrews(long regionId, String uuid) {
		List<Long> joinedCrewIds = crewMemberRepository.findCrewIdsByUuid(uuid);
		List<Crew> scoreNotZeroCrews = crewRepository.findCrewsByScoreNotZero();
		Region userRegion = regionService.getRegionById(regionId);

		List<Crew> filteredCrews = scoreNotZeroCrews.stream()
			.filter(crew -> {
				Region crewRegion = regionService.getRegionById(crew.getRegionId());
				double distance = calculateDistance(userRegion.getLatitude(),
					userRegion.getLongitude(), crewRegion.getLatitude(), crewRegion.getLongitude());
				return distance <= userRegion.getCurrentSelectedRange() && !joinedCrewIds.contains(
					crew.getId());
			})
			.toList();

		return filteredCrews.stream()
			.sorted(Comparator.comparing(Crew::getScore).reversed())
			.limit(5)
			.map(CrewIdNameDTO::toDto)
			.toList();
	}

	@Transactional
	@Override
	public void addCrewScore(CrewScoreDTO crewScoreDTO) {
		Crew crew = getCrewById(crewScoreDTO.getCrewId());
		crewRepository.save(crewScoreDTO.toEntity(crew, 1)); // 1점 추가
	}

	public void minusCrewScore(CrewScoreDTO crewScoreDTO) {
		Crew crew = getCrewById(crewScoreDTO.getCrewId());
		if (crew.getScore() <= 0) {
			throw new GlobalException(ErrorStatus.INVALID_SCORE);
		}
		crewRepository.save(crewScoreDTO.toEntity(crew, -1)); // 1점 감소
	}

	private Crew getCrewById(long crewId) {
		return crewRepository.findById(crewId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));
	}
	// @Override
	// public CrewDetailDTO getCrewInfo(Long crewId) {
	// 	Crew crew = getCrewById(crewId);
	//
	// 	List<String> hashTagList = hashTagRepository.findNamesByCrewId(crewId);
	// 	String addressName = regionRepository.findAddressNameById(crew.getRegionId())
	// 		.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_REGION));
	//
	// 	return CrewDetailDTO.toDto(crew, addressName, hashTagList);
	// }
}
