package hobbiedo.batch.kafka.dto.consumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentUpdateDto {

	private Long boardId;
	private Integer commentCount;
}
