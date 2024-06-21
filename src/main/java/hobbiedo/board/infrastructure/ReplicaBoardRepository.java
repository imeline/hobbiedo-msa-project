package hobbiedo.board.infrastructure;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import hobbiedo.board.domain.ReplicaBoard;

public interface ReplicaBoardRepository extends MongoRepository<ReplicaBoard, String> {

	void deleteByBoardId(Long boardId);

	Optional<ReplicaBoard> findByBoardId(Long boardId);
}
