package hobbiedo.batch.kafka.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import hobbiedo.batch.kafka.dto.producer.BoardCommentCountUpdateDto;
import hobbiedo.batch.kafka.dto.producer.BoardLikeCountUpdateDto;

@Service
public class KafkaProducerService {

	// 통계 게시글 댓글 수 변경 이벤트용 토픽
	private static final String STATISTICS_BOARD_COMMENT_CHANGE_TOPIC = "statistics-board-comment-change-topic";

	// 통계 게시글 좋아요 수 변경 이벤트용 토픽
	private static final String STATISTICS_BOARD_LIKE_CHANGE_TOPIC = "statistics-board-like-change-topic";

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	// 게시글 댓글 수 변경 이벤트 메시지 전송
	public void sendChangeCommentCountMessage(BoardCommentCountUpdateDto eventDto) {
		try {

			kafkaTemplate.send(STATISTICS_BOARD_COMMENT_CHANGE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 좋아요 수 변경 이벤트 메시지 전송
	public void sendChangeLikeCountMessage(BoardLikeCountUpdateDto eventDto) {
		try {

			kafkaTemplate.send(STATISTICS_BOARD_LIKE_CHANGE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
