package hobbiedo.batch.kafka.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import hobbiedo.batch.application.BoardStatsService;
import hobbiedo.batch.kafka.dto.BoardCreateEventDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

	private final BoardStatsService boardStatsService;

	@KafkaListener(topics = "board-create-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "kafkaListenerContainerFactory")
	public void listenToBoardCreateTopic(BoardCreateEventDto eventDto) {

		boardStatsService.createBoardStats(eventDto);
	}
}
