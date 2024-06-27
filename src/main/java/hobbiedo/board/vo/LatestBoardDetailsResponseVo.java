package hobbiedo.board.vo;

import java.time.Instant;
import java.util.ArrayList;
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
	private Instant createdAt;
	private List<String> thumbnailUrl;
	private Integer likeCount;
	private Integer commentCount;

	public LatestBoardDetailsResponseVo(Long boardId, Long crewId, String content,
		String writerUuid,
		Instant createdAt, List<String> thumbnailUrl, Integer likeCount,
		Integer commentCount, String writerName, String writerProfileImageUrl) {
		this.boardId = boardId;
		this.crewId = crewId;
		this.content = content;
		this.writerUuid = writerUuid;
		this.createdAt = createdAt;
		this.thumbnailUrl = thumbnailUrl;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.writerName = writerName;
		this.writerProfileImageUrl = writerProfileImageUrl;
	}

	public static LatestBoardDetailsResponseVo boardDtoToLatestDetailsVo(
		BoardDetailsResponseDto boardResponseDto) {

		List<String> thumbnailUrl = new ArrayList<>();

		if (!boardResponseDto.getImageUrls().isEmpty()) {
			thumbnailUrl.add(boardResponseDto.getImageUrls().get(0));
		}

		return new LatestBoardDetailsResponseVo(
			boardResponseDto.getBoardId(),
			boardResponseDto.getCrewId(),
			boardResponseDto.getContent(),
			boardResponseDto.getWriterUuid(),
			boardResponseDto.getCreatedAt(),
			thumbnailUrl,
			boardResponseDto.getLikeCount(),
			boardResponseDto.getCommentCount(),
			boardResponseDto.getWriterName(),
			boardResponseDto.getWriterProfileImageUrl()
		);
	}
}
