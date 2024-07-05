package hobbiedo.board.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentCountUpdateDto {

	private Long boardId;
	private Integer commentCount;
}
