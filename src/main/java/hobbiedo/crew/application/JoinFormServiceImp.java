package hobbiedo.crew.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.domain.JoinForm;
import hobbiedo.crew.dto.request.JoinFormRequestDTO;
import hobbiedo.crew.dto.response.JoinFormListDTO;
import hobbiedo.crew.dto.response.JoinFormResponseDTO;
import hobbiedo.crew.dto.response.MyJoinFormDTO;
import hobbiedo.crew.infrastructure.jpa.CrewRepository;
import hobbiedo.crew.infrastructure.redis.JoinFormRepository;
import hobbiedo.global.exception.GlobalException;
import hobbiedo.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinFormServiceImp implements JoinFormService {

	private final JoinFormRepository joinFormRepository;
	private final CrewRepository crewRepository;
	private final ValidationService validationService;
	private final CrewService crewService;

	@Transactional
	@Override
	public void addJoinForm(JoinFormRequestDTO joinFormDTO, Long crewId, String uuid) {
		Crew crew = crewRepository.findById(crewId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));

		validationService.isValidCrewMember(crew, uuid);
		if (joinFormRepository.existsByCrewIdAndUuid(crewId, uuid)) {
			throw new GlobalException(ErrorStatus.ALREADY_SEND_JOIN_FORM);
		}
		joinFormRepository.save(joinFormDTO.toEntity(crewId, uuid));
	}

	@Override
	public List<JoinFormListDTO> getJoinFormList(Long crewId, String uuid) {
		validationService.isValidHost(crewId, uuid);
		return joinFormRepository.findByCrewId(crewId)
			.stream()
			.map(JoinFormListDTO::toDto)
			.toList();
	}

	@Override
	public JoinFormResponseDTO getJoinForm(String joinFormId) {
		JoinForm joinForm = getJoinFormById(joinFormId);
		return JoinFormResponseDTO.toDto(joinForm);
	}

	@Transactional
	@Override
	public void acceptJoinForm(String joinFormId, String uuid) {
		JoinForm joinForm = getJoinFormById(joinFormId);
		validationService.isValidHost(joinForm.getCrewId(), uuid);
		Crew crew = crewRepository.findById(joinForm.getCrewId())
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));
		// CrewMember 생성
		crewService.joinCrewMember(crew, joinForm.getUuid());
		// JoinForm 삭제
		joinFormRepository.delete(joinForm);
	}

	@Transactional
	@Override
	public void rejectJoinForm(String joinFormId, String uuid) {
		JoinForm joinForm = getJoinFormById(joinFormId);
		validationService.isValidHost(joinForm.getCrewId(), uuid);
		joinFormRepository.delete(joinForm);
	}

	@Override
	public List<MyJoinFormDTO> getMyJoinForms(String uuid) {
		return joinFormRepository.findByUuid(uuid)
			.stream()
			.map(joinForm -> MyJoinFormDTO.toDto(joinForm,
				crewRepository.findById(joinForm.getCrewId())
					.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW))))
			.toList();
	}

	@Transactional
	@Override
	public void cancelJoinForm(String joinFormId, String uuid) {
		JoinForm joinForm = getJoinFormById(joinFormId);
		if (!joinForm.getUuid().equals(uuid)) {
			throw new GlobalException(ErrorStatus.INVALID_JOIN_FORM_ACCESS);
		}
		joinFormRepository.delete(joinForm);
	}

	private JoinForm getJoinFormById(String joinFormId) {
		return joinFormRepository.findById(joinFormId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_JOIN_FORM));
	}
}
