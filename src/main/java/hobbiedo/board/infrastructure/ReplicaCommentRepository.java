package hobbiedo.board.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import hobbiedo.board.domain.ReplicaComment;

public interface ReplicaCommentRepository extends MongoRepository<ReplicaComment, String> {

	void deleteByCommentId(Long commentId);

	void deleteByBoardId(Long boardId);

	Page<ReplicaComment> findByBoardId(Long boardId, Pageable pageable);
}
