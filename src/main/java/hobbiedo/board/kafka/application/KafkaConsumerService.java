package hobbiedo.board.kafka.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import hobbiedo.board.application.ReplicaBoardService;
import hobbiedo.board.application.ReplicaCommentService;
import hobbiedo.board.kafka.dto.BoardCommentCountUpdateDto;
import hobbiedo.board.kafka.dto.BoardCommentDeleteDto;
import hobbiedo.board.kafka.dto.BoardCommentUpdateDto;
import hobbiedo.board.kafka.dto.BoardCreateEventDto;
import hobbiedo.board.kafka.dto.BoardDeleteEventDto;
import hobbiedo.board.kafka.dto.BoardLikeCountUpdateDto;
import hobbiedo.board.kafka.dto.BoardPinEventDto;
import hobbiedo.board.kafka.dto.BoardUnPinEventDto;
import hobbiedo.board.kafka.dto.BoardUpdateEventDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

	private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
	private final ReplicaBoardService replicaBoardService;
	private final ReplicaCommentService replicaCommentService;

	/**
	 * 게시글 생성 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-create-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "createKafkaListenerContainerFactory")
	public void listenToBoardCreateTopic(BoardCreateEventDto eventDto) {

		log.info("Received board create event: {}", eventDto);

		replicaBoardService.createReplicaBoard(eventDto);
	}

	/**
	 * 게시글 삭제 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-delete-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "deleteKafkaListenerContainerFactory")
	public void listenToBoardDeleteTopic(BoardDeleteEventDto eventDto) {

		replicaBoardService.deleteReplicaBoard(eventDto);
	}

	/**
	 * 게시글 수정 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-update-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "updateKafkaListenerContainerFactory")
	public void listenToBoardUpdateTopic(BoardUpdateEventDto eventDto) {

		replicaBoardService.updateReplicaBoard(eventDto);
	}

	/**
	 * 게시글 고정 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-pin-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "pinKafkaListenerContainerFactory")
	public void listenToBoardPinTopic(BoardPinEventDto eventDto) {

		replicaBoardService.pinReplicaBoard(eventDto);
	}

	/**
	 * 게시글 고정 해제 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-unpin-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "unpinKafkaListenerContainerFactory")
	public void listenToBoardUnPinTopic(BoardUnPinEventDto eventDto) {

		replicaBoardService.unPinReplicaBoard(eventDto);
	}

	/**
	 * 게시글 댓글 테이블 생성 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-comment-update-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "commentCreateKafkaListenerContainerFactory")
	public void listenToCommentCreateTopic(BoardCommentUpdateDto eventDto) {

		replicaCommentService.createReplicaComment(eventDto);
	}

	/**
	 * 게시글 댓글 테이블 삭제 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "board-comment-delete-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "commentDeleteKafkaListenerContainerFactory")
	public void listenToCommentDeleteTopic(BoardCommentDeleteDto eventDto) {

		replicaCommentService.deleteReplicaComment(eventDto);
	}

	/**
	 * 게시글 댓글 수 변경 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "statistics-board-comment-change-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "commentCountChangeKafkaListenerContainerFactory")
	public void listenToCommentCountChangeTopic(BoardCommentCountUpdateDto eventDto) {

		replicaBoardService.changeCommentCount(eventDto);
	}

	/**
	 * 게시글 좋아요 수 변경 이벤트 수신
	 * @param eventDto
	 */
	@KafkaListener(topics = "statistics-board-like-change-topic", groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "likeCountChangeKafkaListenerContainerFactory")
	public void listenToLikeCountChangeTopic(BoardLikeCountUpdateDto eventDto) {

		replicaBoardService.changeLikeCount(eventDto);
	}
}
