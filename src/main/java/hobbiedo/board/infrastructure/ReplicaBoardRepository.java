package hobbiedo.board.infrastructure;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import hobbiedo.board.domain.ReplicaBoard;

public interface ReplicaBoardRepository extends MongoRepository<ReplicaBoard, String> {

	void deleteByBoardId(Long boardId);

	Optional<ReplicaBoard> findByBoardId(Long boardId);

	// crewId로 가장 최근에 작성된 board 를 가져옴
	Optional<ReplicaBoard> findTopByCrewIdOrderByBoardIdDesc(Long crewId);

	Optional<ReplicaBoard> findByPinnedTrueAndCrewId(Long crewId);
}
