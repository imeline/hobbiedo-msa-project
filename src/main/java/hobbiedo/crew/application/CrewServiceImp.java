package hobbiedo.crew.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.chat.application.ChatService;
import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.dto.request.CrewDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;
import hobbiedo.crew.infrastructure.CrewMemberRepository;
import hobbiedo.crew.infrastructure.CrewRepository;
import hobbiedo.crew.infrastructure.HashTagRepository;
import hobbiedo.global.exception.GlobalException;
import hobbiedo.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewServiceImp implements CrewService {

	private final CrewRepository crewRepository;
	private final CrewMemberRepository crewMemberRepository;
	private final HashTagRepository hashTagRepository;
	private final ChatService chatService;

	@Transactional
	@Override
	public CrewIdDTO createCrew(CrewDTO crewDTO, String uuid) {
		isVaildCreateCrew(crewDTO, uuid);
		// HashTag 개수 체크
		if (crewDTO.getHashTagList().size() > 5) {
			throw new GlobalException(ErrorStatus.INVALID_HASH_TAG_COUNT);
		}
		// Crew 생성
		Crew crew = crewRepository.save(crewDTO.toCrewEntity());

		// CrewMember 생성
		crewMemberRepository.save(crewDTO.toCrewMemberEntity(crew, uuid));

		// HashTag 생성
		if (crewDTO.getHashTagList() != null) {
			crewDTO.getHashTagList().forEach(hashTagName -> {
				hashTagRepository.save(crewDTO.toHashTagEntity(crew, hashTagName));
			});
		}
		// ChatLastStatus 생성
		chatService.createChatStatus(crew.getId(), uuid);

		return CrewIdDTO.toDto(crew.getId());
	}

	private void isVaildCreateCrew(CrewDTO crewDTO, String uuid) {
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

}
