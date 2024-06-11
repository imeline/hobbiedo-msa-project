package hobbiedo.board.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.board.domain.BoardStats;

@Repository
public interface BoardStatsRepository extends JpaRepository<BoardStats, Long> {
}
