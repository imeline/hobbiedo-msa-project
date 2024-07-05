package hobbiedo.batch.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.batch.domain.BoardStats;

@Repository
public interface BoardStatsRepository extends JpaRepository<BoardStats, Long> {

	// 게시글 통계 삭제
	void deleteByBoardId(Long boardId);

	Optional<BoardStats> findByBoardId(Long boardId);
}
