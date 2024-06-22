package hobbiedo.crew.kafka.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import hobbiedo.crew.application.ReplicaCrewService;
import hobbiedo.crew.kafka.dto.CrewEntryExitDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaCrewConsumerService {

	private final ReplicaCrewService replicaCrewService;

	// 소모임 생성 이벤트 수신 - crew 생성, crewMember 추가
	@KafkaListener(topics = "create-crew-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "crewEntryExitKafkaListenerContainerFactory")
	public void listenToCreateCrewTopic(CrewEntryExitDTO eventDto) {
		replicaCrewService.createCrew(eventDto);
	}

	// 소모임 가입 이벤트 수신 - crewMember 추가
	@KafkaListener(topics = "join-crew-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "crewEntryExitKafkaListenerContainerFactory")
	public void listenToJoinCrewTopic(CrewEntryExitDTO eventDto) {
		replicaCrewService.addCrewMember(eventDto);
	}

	// 소모임 탈퇴, 강퇴 이벤트 수신 - crewMember 삭제
	@KafkaListener(topics = {"exit-crew-topic",
		"force-exit-crew-topic"}, groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "crewEntryExitKafkaListenerContainerFactory")
	public void listenToExitCrewTopic(CrewEntryExitDTO eventDto) {
		replicaCrewService.deleteCrewMember(eventDto);
	}
}
