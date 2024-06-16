package hobbiedo.batch.kafka.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import hobbiedo.batch.application.BoardStatsService;
import hobbiedo.batch.kafka.dto.BoardCreateEventDto;
import hobbiedo.batch.kafka.dto.BoardDeleteEventDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

	private final BoardStatsService boardStatsService;

	/**
	 * 게시글 생성 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-create-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "createKafkaListenerContainerFactory")
	public void listenToBoardCreateTopic(BoardCreateEventDto eventDto) {

		boardStatsService.createBoardStats(eventDto);
	}

	/**
	 * 게시글 삭제 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-delete-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "deleteKafkaListenerContainerFactory")
	public void listenToBoardDeleteTopic(BoardDeleteEventDto eventDto) {

		boardStatsService.deleteBoardStats(eventDto);
	}
}
