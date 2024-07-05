package hobbiedo.crew.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.domain.CrewMember;
import hobbiedo.crew.domain.JoinForm;
import hobbiedo.crew.dto.request.JoinFormRequestDTO;
import hobbiedo.crew.dto.response.JoinFormListDTO;
import hobbiedo.crew.dto.response.JoinFormResponseDTO;
import hobbiedo.crew.dto.response.MyJoinFormDTO;
import hobbiedo.crew.infrastructure.CrewMemberRepository;
import hobbiedo.crew.infrastructure.CrewRepository;
import hobbiedo.crew.infrastructure.JoinFormRepository;
import hobbiedo.global.exception.GlobalException;
import hobbiedo.global.status.ErrorStatus;
import hobbiedo.notification.application.NotificationService;
import hobbiedo.notification.type.NotificationType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinFormServiceImp implements JoinFormService {

	private final JoinFormRepository joinFormRepository;
	private final CrewRepository crewRepository;
	private final ValidationService validationService;
	private final CrewService crewService;
	private final NotificationService notificationService;
	private final CrewMemberRepository crewMemberRepository;

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
		// 알림
		CrewMember host = crewMemberRepository.findByCrewIdAndRole(crewId, 1)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW_MEMBER));
		notificationService.sendNotification(host.getUuid(), crew.getName(),
			NotificationType.ADD_JOIN_FORM.getContent(), crew.getProfileUrl());
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
		// 알림
		notificationService.sendNotification(joinForm.getUuid(), crew.getName(),
			NotificationType.ACCEPT_JOIN_FORM.getContent(),
			crew.getProfileUrl());
	}

	@Transactional
	@Override
	public void rejectJoinForm(String joinFormId, String uuid) {
		JoinForm joinForm = getJoinFormById(joinFormId);
		validationService.isValidHost(joinForm.getCrewId(), uuid);
		joinFormRepository.delete(joinForm);
		// 알림
		Crew crew = crewRepository.findById(joinForm.getCrewId())
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CREW));
		notificationService.sendNotification(joinForm.getUuid(), crew.getName(),
			NotificationType.REJECT_JOIN_FORM.getContent(),
			crew.getProfileUrl());
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
