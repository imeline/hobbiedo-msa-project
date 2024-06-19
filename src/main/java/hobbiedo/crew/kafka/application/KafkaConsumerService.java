package hobbiedo.crew.kafka.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import hobbiedo.crew.application.CrewService;
import hobbiedo.crew.kafka.dto.CrewScoreDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

	private final CrewService crewService;

	// 게시글 생성 이벤트 수신 - 팀 점수 1 증가
	@KafkaListener(topics = "crew-score-increase-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "crewScoreKafkaListenerContainerFactory")
	public void listenToScoreAddTopic(CrewScoreDTO eventDto) {
		crewService.addCrewScore(eventDto);
	}

	// 게시글 삭제 이벤트 수신 - 팀 점수 1 감소
	@KafkaListener(topics = "crew-score-decrease-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "crewScoreKafkaListenerContainerFactory")
	public void listenToScoreMinusTopic(CrewScoreDTO eventDto) {
		crewService.minusCrewScore(eventDto);
	}
}
