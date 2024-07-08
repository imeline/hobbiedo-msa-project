package hobbiedo.member.kafka.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import hobbiedo.member.application.ReplicaMemberService;
import hobbiedo.member.kafka.dto.ModifyProfileDTO;
import hobbiedo.member.kafka.dto.SignUpDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaMemberConsumerService {

	private final ReplicaMemberService replicaMemberService;

	@KafkaListener(topics = "sign-up-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "signUpKafkaListenerContainerFactory")
	public void listenToScoreAddTopic(SignUpDTO eventDto) {
		replicaMemberService.createMemberProfile(eventDto);
	}

	@KafkaListener(topics = "update-profile-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "modifyProfileKafkaListenerContainerFactory")
	public void updateMemberProfile(ModifyProfileDTO eventDto) {
		replicaMemberService.updateMemberProfile(eventDto);
	}
}
