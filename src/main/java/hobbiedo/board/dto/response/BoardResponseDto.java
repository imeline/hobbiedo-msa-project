package hobbiedo.board.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {

	private Long boardId;
	private String title;
	private String content;
	private boolean pinned; // 고정 여부
	private Long likeCount;
	private Long commentCount;
	private List<String> imageUrls;
}
