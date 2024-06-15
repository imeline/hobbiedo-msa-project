package hobbiedo.batch.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.batch.domain.BoardStats;

@Repository
public interface BoardStatsRepository extends JpaRepository<BoardStats, Long> {
}
