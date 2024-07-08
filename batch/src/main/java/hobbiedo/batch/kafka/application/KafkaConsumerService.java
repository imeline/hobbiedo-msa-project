package hobbiedo.batch.kafka.application;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import hobbiedo.batch.application.BoardStatsService;
import hobbiedo.batch.kafka.dto.consumer.BoardCommentDeleteDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCommentUpdateDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCreateEventDto;
import hobbiedo.batch.kafka.dto.consumer.BoardDeleteEventDto;
import hobbiedo.batch.kafka.dto.consumer.BoardLikeUpdateDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

	private final BoardStatsService boardStatsService;

	private final RedisTemplate<String, Object> redisTemplate;

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
	 * 게시글 댓글 생성 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-comment-update-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "commentUpdateKafkaListenerContainerFactory")
	public void listenToBoardCommentUpdateTopic(BoardCommentUpdateDto eventDto) {

		// 레디스에 해당 게시글 댓글 수 +1
		// key : board:{게시글번호}:comment:count
		String boardCommentCountKey = "board:" + eventDto.getBoardId() + ":comment:count";

		redisTemplate.opsForValue().increment(boardCommentCountKey, 1);
	}

	/**
	 * 게시글 댓글 삭제 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-comment-delete-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "commentDeleteKafkaListenerContainerFactory")
	public void listenToBoardCommentDeleteTopic(BoardCommentDeleteDto eventDto) {

		// 레디스에 해당 게시글 댓글 수 -1
		// key : board:{게시글번호}:comment:count
		String boardCommentCountKey = "board:" + eventDto.getBoardId() + ":comment:count";

		redisTemplate.opsForValue().decrement(boardCommentCountKey, 1);
	}

	/**
	 * 게시글 좋아요 수 증가 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-like-update-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "likeUpdateKafkaListenerContainerFactory")
	public void listenToBoardLikeUpdateTopic(BoardLikeUpdateDto eventDto) {

		// 레디스에 해당 게시글 좋아요 수 +1
		// key : board:{게시글번호}:like:count
		String boardLikeCountKey = "board:" + eventDto.getBoardId() + ":like:count";

		redisTemplate.opsForValue().increment(boardLikeCountKey, 1);
	}

	/**
	 * 게시글 좋아요 수 감소 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-like-delete-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "likeDeleteKafkaListenerContainerFactory")
	public void listenToBoardLikeDeleteTopic(BoardLikeUpdateDto eventDto) {

		// 레디스에 해당 게시글 좋아요 수 -1
		// key : board:{게시글번호}:like:count
		String boardLikeCountKey = "board:" + eventDto.getBoardId() + ":like:count";

		redisTemplate.opsForValue().decrement(boardLikeCountKey, 1);
	}
}
