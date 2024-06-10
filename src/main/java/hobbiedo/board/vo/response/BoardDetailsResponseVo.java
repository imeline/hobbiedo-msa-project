package hobbiedo.board.vo.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import hobbiedo.board.dto.response.BoardDetailsResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 조회 응답")
public class BoardDetailsResponseVo {

	private Long boardId;
	private String title;
	private String content;
	private String writerUuid;
	private Long likeCount;
	private Long commentCount;
	private boolean pinned; // 고정 여부
	private String updatedAt; // 수정일
	private boolean updated; // 수정 여부
	private List<String> imageUrls;

	public BoardDetailsResponseVo(Long boardId, String title, String content, String writerUuid,
			Long likeCount,
			Long commentCount, boolean pinned, LocalDateTime updatedAt, boolean updated,
			List<String> imageUrls) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.writerUuid = writerUuid;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.pinned = pinned;

		// 날짜, 시간 형식 지정
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		this.updatedAt = updatedAt.format(formatter); // 수정일 (날짜, 시간 형식으로 변환
		this.updated = updated;
		this.imageUrls = imageUrls;
	}

	public static BoardDetailsResponseVo boardDtoToDetailsVo(BoardDetailsResponseDto boardDto) {
		return new BoardDetailsResponseVo(
				boardDto.getBoardId(),
				boardDto.getTitle(),
				boardDto.getContent(),
				boardDto.getWriterUuid(),
				boardDto.getLikeCount(),
				boardDto.getCommentCount(),
				boardDto.isPinned(),
				boardDto.getUpdatedAt(),
				boardDto.isUpdated(),
				boardDto.getImageUrls()
		);
	}
}
