package hobbiedo.board.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import hobbiedo.board.domain.BoardImage;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
}
