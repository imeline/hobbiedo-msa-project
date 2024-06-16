package hobbiedo.crew.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.chat.application.ChatService;
import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.domain.CrewMember;
import hobbiedo.crew.domain.HashTag;
import hobbiedo.crew.domain.JoinForm;
import hobbiedo.crew.dto.request.CrewOutDTO;
import hobbiedo.crew.dto.request.CrewRequestDTO;
import hobbiedo.crew.dto.request.JoinFormRequestDTO;
import hobbiedo.crew.dto.response.CrewDetailDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;
import hobbiedo.crew.dto.response.CrewNameDTO;
import hobbiedo.crew.dto.response.CrewProfileDTO;
import hobbiedo.crew.dto.response.CrewResponseDTO;
import hobbiedo.crew.dto.response.JoinFormListDTO;
import hobbiedo.crew.dto.response.JoinFormResponseDTO;
import hobbiedo.crew.infrastructure.jpa.CrewMemberRepository;
import hobbiedo.crew.infrastructure.jpa.CrewRepository;
import hobbiedo.crew.infrastructure.jpa.HashTagRepository;
import hobbiedo.crew.infrastructure.redis.JoinFormRepository;
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
	private final JoinFormRepository joinFormRepository;
	private final ChatService chatService;
	private final RegionRepository regionRepository;

	@Transactional
	@Override
	public CrewIdDTO createCrew(CrewRequestDTO crewDTO, String uuid) {
		isVaildCrew(crewDTO, uuid);
		// 이미 만든 방이 5개 이상인지 체크
		if (crewMemberRepository.countByUuidAndRole(uuid, 1) >= 5) {
			throw new GlobalException(ErrorStatus.INVALID_MAX_HOST_COUNT);
		}
		// Crew 생성
		Crew crew = crewRepository.save(crewDTO.toCrewEntity());
		// 방장 CrewMember 생성
		crewMemberRepository.save(crewDTO.toCrewMemberEntity(crew, uuid));
		// HashTag 생성
		createHashTag(crew, crewDTO.getHashTagList());
		// ChatLastStatus 생성
		chatService.createChatStatus(crew.getId(), uuid);

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

	private void isVaildCrew(CrewRequestDTO crewDTO, String uuid) {
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

	@Transactional
	@Override
	public void joinCrew(Long crewId, String uuid) {
		Crew crew = crewRepository.findById(crewId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));
		isVaildCrewMember(crew, uuid);
		joinCrewMember(crew, uuid);
	}

	@Transactional
	protected void joinCrewMember(Crew crew, String uuid) {
		isVaildFullCrew(crew);
		// CrewMember 생성
		crewMemberRepository.save(CrewMember.builder()
			.crew(crew)
			.uuid(uuid)
			.role(0) // 일반회원
			.build());
		// 참여인원 증가
		changeCrewParticipant(crew, 1);
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

	private void isVaildFullCrew(Crew crew) {
		// 가입인원 다 찾는지 확인
		if (crew.getCurrentParticipant() >= 100) {
			throw new GlobalException(ErrorStatus.INVALID_MAX_PARTICIPANT);
		}
	}

	private void isVaildCrewMember(Crew crew, String uuid) {
		// 블랙리스트인지 확인
		if (crewMemberRepository.existsByCrewIdAndUuidAndRole(crew.getId(), uuid, 2)) {
			throw new GlobalException(ErrorStatus.INVALID_BANNED);
		}
		// 이미 가입한 방인지 체크
		if (crewMemberRepository.existsByCrewIdAndUuid(crew.getId(), uuid)) {
			throw new GlobalException(ErrorStatus.ALREADY_JOINED_CREW);
		}
	}

	@Override
	public CrewDetailDTO getCrewInfo(Long crewId) {
		Crew crew = crewRepository.findById(crewId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));

		List<String> hashTagList = hashTagRepository.findNamesByCrewId(crewId);
		String addressName = regionRepository.findAddressNameById(crew.getRegionId())
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_REGION));

		return CrewDetailDTO.toDto(crew, addressName, hashTagList);
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

	@Transactional
	@Override
	public void addJoinForm(JoinFormRequestDTO joinFormDTO, Long crewId, String uuid) {
		Crew crew = crewRepository.findById(crewId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));

		isVaildCrewMember(crew, uuid);
		if (joinFormRepository.existsByCrewIdAndUuid(crewId, uuid)) {
			throw new GlobalException(ErrorStatus.ALREADY_SEND_JOIN_FORM);
		}
		joinFormRepository.save(joinFormDTO.toEntity(crewId, uuid));
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
		Crew crew = crewRepository.findById(crewId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));
		return CrewNameDTO.toDto(crew.getName());
	}

	@Transactional
	@Override
	public void deleteCrewMember(Long crewId, String uuid) {
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
	}

	@Override
	public CrewResponseDTO getCrew(Long crewId) {
		Crew crew = crewRepository.findById(crewId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));
		List<String> hashTagList = hashTagRepository.findNamesByCrewId(crewId);
		return CrewResponseDTO.toDto(crew, hashTagList);
	}

	private void isValidHost(Long crewId, String uuid) {
		if (crewMemberRepository.findByCrewIdAndRole(crewId, 1)
			.map(crewMember -> !crewMember.getUuid().equals(uuid))
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW_ID_OR_HOST))) {
			throw new GlobalException(ErrorStatus.INVALID_HOST_ACCESS);
		}
	}

	@Transactional
	@Override
	public void modifyCrew(CrewRequestDTO crewDTO, Long crewId, String uuid) {
		isValidHost(crewId, uuid);
		isVaildCrew(crewDTO, uuid);
		Crew crew = crewRepository.findById(crewId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));
		// Crew 수정
		crewRepository.save(crewDTO.toModifyCrewEntity(crew));
		// HashTag 삭제
		hashTagRepository.deleteByCrewId(crewId);
		// HashTag 생성
		createHashTag(crew, crewDTO.getHashTagList());
	}

	@Transactional
	@Override
	public void forcedExitCrew(CrewOutDTO crewOutDTO, Long crewId, String uuid) {
		isValidHost(crewId, uuid);
		// 블랙리스트로 변경
		CrewMember crewMember = crewMemberRepository.findByCrewIdAndUuid(crewId,
				crewOutDTO.getOutUuid())
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW_MEMBER));
		crewMemberRepository.save(crewOutDTO.toCrewMemberEntity(crewMember));
		// 참여 인원 감소
		changeCrewParticipant(crewMember.getCrew(), -1);
	}

	@Override
	public List<JoinFormListDTO> getJoinFormList(Long crewId, String uuid) {
		isValidHost(crewId, uuid);
		return joinFormRepository.findByCrewId(crewId)
			.stream()
			.map(JoinFormListDTO::toDto)
			.toList();
	}

	@Override
	public JoinFormResponseDTO getJoinForm(String joinFormId) {
		JoinForm joinForm = joinFormRepository.findById(joinFormId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_JOIN_FORM));
		return JoinFormResponseDTO.toDto(joinForm);
	}

	@Transactional
	@Override
	public void acceptJoinForm(String joinFormId, String uuid) {
		JoinForm joinForm = joinFormRepository.findById(joinFormId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_JOIN_FORM));
		isValidHost(joinForm.getCrewId(), uuid);
		Crew crew = crewRepository.findById(joinForm.getCrewId())
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));
		// CrewMember 생성
		joinCrewMember(crew, joinForm.getUuid());
		// JoinForm 삭제
		joinFormRepository.delete(joinForm);
	}
}
