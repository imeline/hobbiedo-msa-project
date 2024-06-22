package hobbiedo.board.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

import hobbiedo.board.domain.ReplicaComment;

public interface ReplicaCommentRepository extends MongoRepository<ReplicaComment, String> {

	void deleteByCommentId(Long commentId);

	void deleteByBoardId(Long boardId);
}
