package hobbiedo.board.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import hobbiedo.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
