package hobbiedo.batch.kafka.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import hobbiedo.batch.application.BoardStatsService;
import hobbiedo.batch.kafka.dto.BoardCommentUpdateDto;
import hobbiedo.batch.kafka.dto.BoardCreateEventDto;
import hobbiedo.batch.kafka.dto.BoardDeleteEventDto;
import hobbiedo.batch.kafka.dto.BoardLikeUpdateDto;
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

	/**
	 * 게시글 댓글 수 증가 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-comment-update-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "commentUpdateKafkaListenerContainerFactory")
	public void listenToBoardCommentUpdateTopic(BoardCommentUpdateDto eventDto) {

		boardStatsService.updateBoardCommentStats(eventDto);
	}

	/**
	 * 게시글 댓글 수 감소 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-comment-delete-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "commentDeleteKafkaListenerContainerFactory")
	public void listenToBoardCommentDeleteTopic(BoardCommentUpdateDto eventDto) {

		boardStatsService.deleteBoardCommentStats(eventDto);
	}

	/**
	 * 게시글 좋아요 수 증가 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-like-update-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "likeUpdateKafkaListenerContainerFactory")
	public void listenToBoardLikeUpdateTopic(BoardLikeUpdateDto eventDto) {

		boardStatsService.updateBoardLikeStats(eventDto);
	}

	/**
	 * 게시글 좋아요 수 감소 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-like-delete-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "likeDeleteKafkaListenerContainerFactory")
	public void listenToBoardLikeDeleteTopic(BoardLikeUpdateDto eventDto) {

		boardStatsService.deleteBoardLikeStats(eventDto);
	}
}
