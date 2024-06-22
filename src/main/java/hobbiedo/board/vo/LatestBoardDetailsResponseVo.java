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
public class LatestBoardDetailsResponseVo {

	private Long boardId;
	private Long crewId;
	private String content;
	private String writerUuid;
	private String writerName;
	private String writerProfileImageUrl;
	private LocalDateTime createdAt;
	private String thumbnailImageUrl;
	private Integer likeCount;
	private Integer commentCount;

	public LatestBoardDetailsResponseVo(Long boardId, Long crewId, String content,
		String writerUuid,
		LocalDateTime createdAt, List<String> imageUrls, Integer likeCount,
		Integer commentCount, String writerName, String writerProfileImageUrl) {
		this.boardId = boardId;
		this.crewId = crewId;
		this.content = content;
		this.writerUuid = writerUuid;
		this.createdAt = createdAt;
		this.thumbnailImageUrl =
			(imageUrls != null && !imageUrls.isEmpty()) ? imageUrls.get(0) : null;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.writerName = writerName;
		this.writerProfileImageUrl = writerProfileImageUrl;
	}

	public static LatestBoardDetailsResponseVo boardDtoToLatestDetailsVo(
		BoardDetailsResponseDto boardResponseDto) {

		return new LatestBoardDetailsResponseVo(
			boardResponseDto.getBoardId(),
			boardResponseDto.getCrewId(),
			boardResponseDto.getContent(),
			boardResponseDto.getWriterUuid(),
			boardResponseDto.getCreatedAt(),
			boardResponseDto.getImageUrls(),
			boardResponseDto.getLikeCount(),
			boardResponseDto.getCommentCount(),
			boardResponseDto.getWriterName(),
			boardResponseDto.getWriterProfileImageUrl()
		);
	}
}
