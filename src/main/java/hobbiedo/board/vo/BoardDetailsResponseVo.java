package hobbiedo.board.vo;

import java.time.LocalDateTime;
import java.util.List;

import hobbiedo.board.dto.BoardDetailsResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 조회 응답")
public class BoardDetailsResponseVo {

	private Long boardId;
	private Long crewId;
	private String content;
	private String writerUuid;
	private String writerName;
	private String writerProfileImageUrl;
	private boolean pinned;
	private LocalDateTime createdAt;
	private boolean updated;
	private List<String> imageUrls;
	private Integer likeCount;
	private Integer commentCount;

	public BoardDetailsResponseVo(Long boardId, Long crewId, String content,
		String writerUuid,
		boolean pinned, LocalDateTime createdAt, boolean updated, List<String> imageUrls,
		Integer likeCount,
		Integer commentCount, String writerName, String writerProfileImageUrl) {
		this.boardId = boardId;
		this.crewId = crewId;
		this.content = content;
		this.writerUuid = writerUuid;
		this.pinned = pinned;
		this.createdAt = createdAt;
		this.updated = updated;
		this.imageUrls = imageUrls;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.writerName = writerName;
		this.writerProfileImageUrl = writerProfileImageUrl;
	}

	public static BoardDetailsResponseVo boardDtoToDetailsVo(
		BoardDetailsResponseDto boardResponseDto) {

		List<String> imageUrls = boardResponseDto.getImageUrls();
		if (imageUrls.isEmpty()) {
			imageUrls = null;
		}

		return new BoardDetailsResponseVo(
			boardResponseDto.getBoardId(),
			boardResponseDto.getCrewId(),
			boardResponseDto.getContent(),
			boardResponseDto.getWriterUuid(),
			boardResponseDto.isPinned(),
			boardResponseDto.getCreatedAt(),
			boardResponseDto.isUpdated(),
			imageUrls,
			boardResponseDto.getLikeCount(),
			boardResponseDto.getCommentCount(),
			boardResponseDto.getWriterName(),
			boardResponseDto.getWriterProfileImageUrl()
		);
	}
}
