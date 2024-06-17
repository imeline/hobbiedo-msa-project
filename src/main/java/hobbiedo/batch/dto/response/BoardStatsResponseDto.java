package hobbiedo.batch.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardStatsResponseDto {

	private Integer commentCount; // 댓글 수
	private Integer likeCount; // 좋아요 수
}
