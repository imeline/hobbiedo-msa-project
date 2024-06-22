package hobbiedo.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.global.api.code.status.ErrorStatus;
import hobbiedo.global.api.exception.handler.ReadOnlyExceptionHandler;
import hobbiedo.member.domain.MemberProfile;
import hobbiedo.member.infrastructure.ReplicaMemberRepository;
import hobbiedo.member.kafka.dto.ModifyProfileDTO;
import hobbiedo.member.kafka.dto.SignUpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReplicaMemberServiceImp implements ReplicaMemberService {
	private final ReplicaMemberRepository replicaMemberRepository;

	@Transactional
	@Override
	public void createMemberProfile(SignUpDTO signUpDTO) {
		log.info("Sercvice : " + signUpDTO.toString());
		replicaMemberRepository.save(signUpDTO.toEntity());
	}

	@Transactional
	@Override
	public void updateMemberProfile(ModifyProfileDTO modifyProfileDTO) {
		MemberProfile memberProfile = replicaMemberRepository.findByUuid(modifyProfileDTO.getUuid())
			.orElseThrow(() -> new ReadOnlyExceptionHandler(ErrorStatus.NOT_FOUND_MEMBER_PROFILE));
		replicaMemberRepository.save(modifyProfileDTO.toEntity(memberProfile));
	}
}
