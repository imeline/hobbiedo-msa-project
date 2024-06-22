package hobbiedo.board.application;

import hobbiedo.board.kafka.dto.BoardCommentDeleteDto;
import hobbiedo.board.kafka.dto.BoardCommentUpdateDto;

public interface ReplicaCommentService {

	void createReplicaComment(BoardCommentUpdateDto eventDto);

	void deleteReplicaComment(BoardCommentDeleteDto eventDto);
}
