package hobbiedo.board.vo.response;

import java.util.List;

import hobbiedo.board.dto.response.BoardResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 조회 응답")
public class BoardResponseVo {

	private Long boardId;
	private String title;
	private String content;
	private boolean pinned; // 고정 여부
	private Long likeCount;
	private Long commentCount;
	private List<String> imageUrls;

	public BoardResponseVo(Long boardId, String title, String content, boolean pinned,
			Long likeCount, Long commentCount, List<String> imageUrls) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.pinned = pinned;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.imageUrls = imageUrls;
	}

	public static BoardResponseVo boardDtoToVo(BoardResponseDto boardDto) {
		return new BoardResponseVo(
				boardDto.getBoardId(),
				boardDto.getTitle(),
				boardDto.getContent(),
				boardDto.isPinned(),
				boardDto.getLikeCount(),
				boardDto.getCommentCount(),
				boardDto.getImageUrls()
		);
	}
}
