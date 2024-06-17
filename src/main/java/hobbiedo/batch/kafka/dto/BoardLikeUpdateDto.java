package hobbiedo.batch.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikeUpdateDto {

	private Long boardId;
	private Integer likeCount = 0;
}
