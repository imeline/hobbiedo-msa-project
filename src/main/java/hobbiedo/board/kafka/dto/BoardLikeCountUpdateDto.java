package hobbiedo.board.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikeCountUpdateDto {

	private Long boardId;
	private Integer likeCount;
}
