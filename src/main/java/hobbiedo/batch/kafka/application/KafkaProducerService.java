package hobbiedo.batch.kafka.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import hobbiedo.batch.kafka.dto.producer.BoardCommentCountUpdateDto;
import hobbiedo.batch.kafka.dto.producer.BoardLikeCountUpdateDto;

@Service
public class KafkaProducerService {

	// 통계 게시글 댓글 수 증가 이벤트용 토픽
	private static final String STATISTICS_BOARD_COMMENT_UPDATE_TOPIC = "statistics-board-comment-update-topic";

	// 통계 게시글 댓글 수 감소 이벤트용 토픽
	private static final String STATISTICS_BOARD_COMMENT_DELETE_TOPIC = "statistics-board-comment-delete-topic";

	// 통계 게시글 좋아요 수 증가 이벤트용 토픽
	private static final String STATISTICS_BOARD_LIKE_UPDATE_TOPIC = "statistics-board-like-update-topic";

	// 통계 게시글 좋아요 수 감소 이벤트용 토픽
	private static final String STATISTICS_BOARD_LIKE_DELETE_TOPIC = "statistics-board-like-delete-topic";

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	// 통계 게시글 댓글 수 증가 이벤트 메시지 전송
	public void sendUpdateCommentCountMessage(BoardCommentCountUpdateDto eventDto) {
		try {

			kafkaTemplate.send(STATISTICS_BOARD_COMMENT_UPDATE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 통계 게시글 댓글 수 감소 이벤트 메시지 전송
	public void sendDeleteCommentCountMessage(BoardCommentCountUpdateDto eventDto) {
		try {

			kafkaTemplate.send(STATISTICS_BOARD_COMMENT_DELETE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 좋아요 수 증가 이벤트 메시지 전송
	public void sendUpdateLikeCountMessage(BoardLikeCountUpdateDto eventDto) {
		try {

			kafkaTemplate.send(STATISTICS_BOARD_LIKE_UPDATE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 좋아요 수 감소 이벤트 메시지 전송
	public void sendDeleteLikeCountMessage(BoardLikeCountUpdateDto eventDto) {
		try {

			kafkaTemplate.send(STATISTICS_BOARD_LIKE_DELETE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
