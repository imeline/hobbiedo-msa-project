package hobbiedo.board.application;

import org.springframework.data.domain.Pageable;

import hobbiedo.board.dto.CommentListResponseDto;
import hobbiedo.board.kafka.dto.BoardCommentDeleteDto;
import hobbiedo.board.kafka.dto.BoardCommentUpdateDto;

public interface ReplicaCommentService {

	void createReplicaComment(BoardCommentUpdateDto eventDto);

	void deleteReplicaComment(BoardCommentDeleteDto eventDto);

	CommentListResponseDto getCommentList(Long boardId, Pageable page);
}
