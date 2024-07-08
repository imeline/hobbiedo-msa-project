package hobbiedo.user.auth.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.user.auth.global.api.code.status.ErrorStatus;
import hobbiedo.user.auth.global.exception.MemberExceptionHandler;
import hobbiedo.user.auth.kafka.application.KafkaProducerService;
import hobbiedo.user.auth.kafka.dto.ModifyProfileDTO;
import hobbiedo.user.auth.member.domain.Member;
import hobbiedo.user.auth.member.dto.request.ProfileRequestDto;
import hobbiedo.user.auth.member.dto.response.ProfileResponseDto;
import hobbiedo.user.auth.member.infrastructure.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

	private final MemberProfileRepository memberProfileRepository;
	private final KafkaProducerService kafkaProducerService;

	public ProfileResponseDto getProfile(String uuid) {

		Member member = memberProfileRepository.findByUuid(uuid)
			.orElseThrow(() -> new MemberExceptionHandler(ErrorStatus.NOT_FOUND_MEMBER));

		return ProfileResponseDto.builder()
			.uuid(member.getUuid())
			.name(member.getName())
			.email(member.getEmail())
			.phoneNumber(member.getPhoneNumber())
			.birth(member.getBirth())
			.gender(member.getGender())
			.profileImageUrl(member.getImageUrl())
			.profileMessage(member.getProfileMessage())
			.build();
	}

	@Transactional
	public void updateProfile(String uuid, ProfileRequestDto profileRequestDto) {

		Member member = memberProfileRepository.findByUuid(uuid)
			.orElseThrow(() -> new MemberExceptionHandler(ErrorStatus.NOT_FOUND_MEMBER));

		// 상태 메세지가 공백 포함 50자 이상일 경우 예외 처리
		if (profileRequestDto.getProfileMessage().length() > 52) {
			throw new MemberExceptionHandler(ErrorStatus.PROFILE_MESSAGE_LENGTH_EXCEED);
		}

		Member updateMember = Member.builder()
			.id(member.getId())
			.name(member.getName())
			.uuid(member.getUuid())
			.email(member.getEmail())
			.phoneNumber(member.getPhoneNumber())
			.active(member.getActive())
			.gender(member.getGender())
			.birth(member.getBirth())
			.profileMessage(profileRequestDto.getProfileMessage())
			.imageUrl(profileRequestDto.getProfileImageUrl())
			.build();

		memberProfileRepository.save(updateMember);
		kafkaProducerService.setUpdateProfileTopic(
			ModifyProfileDTO.toDto(updateMember.getUuid(), updateMember.getImageUrl()));
	}
}
