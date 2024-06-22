package hobbiedo.member.application;

import org.springframework.stereotype.Service;

import hobbiedo.global.api.code.status.ErrorStatus;
import hobbiedo.global.api.exception.handler.ReadOnlyExceptionHandler;
import hobbiedo.member.domain.MemberProfile;
import hobbiedo.member.infrastructure.ReplicaMemberRepository;
import hobbiedo.member.kafka.dto.ModifyProfileDTO;
import hobbiedo.member.kafka.dto.SignUpDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplicaMemberServiceImp implements ReplicaMemberService {
	private final ReplicaMemberRepository replicaMemberRepository;

	@Override
	public void createMemberProfile(SignUpDTO signUpDTO) {
		replicaMemberRepository.save(signUpDTO.toEntity());
	}

	@Override
	public void updateMemberProfile(ModifyProfileDTO modifyProfileDTO) {
		MemberProfile memberProfile = replicaMemberRepository.findByUuid(modifyProfileDTO.getUuid())
			.orElseThrow(() -> new ReadOnlyExceptionHandler(ErrorStatus.NOT_FOUND_MEMBER_PROFILE));
		replicaMemberRepository.save(modifyProfileDTO.toEntity(memberProfile));
	}
}
