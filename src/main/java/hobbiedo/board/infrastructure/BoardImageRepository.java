package hobbiedo.board.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.board.domain.BoardImage;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

	// @Query("SELECT bi.url FROM BoardImage bi WHERE bi.board.id = :boardId")
	// List<String> findImageUrlsByBoardId(@Param("boardId") Long boardId);

	List<BoardImage> findByBoardId(Long boardId);
}
