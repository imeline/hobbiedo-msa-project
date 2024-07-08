package hobbiedo.batch.vo.response;

import hobbiedo.batch.dto.response.BoardStatsResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시판 통계 조회 응답")
public class BoardStatsResponseVo {

	private Integer commentCount; // 댓글 수
	private Integer likeCount; // 좋아요 수

	public BoardStatsResponseVo(Integer commentCount, Integer likeCount) {
		this.commentCount = commentCount;
		this.likeCount = likeCount;
	}

	public static BoardStatsResponseVo boardStatsDtoToVo(
		BoardStatsResponseDto boardStatsResponseDto) {
		return new BoardStatsResponseVo(
			boardStatsResponseDto.getCommentCount(),
			boardStatsResponseDto.getLikeCount()
		);
	}
}
