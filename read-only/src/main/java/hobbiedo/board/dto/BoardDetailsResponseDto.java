package hobbiedo.board.dto;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailsResponseDto {

	private Long boardId;
	private Long crewId;
	private String content;
	private String writerUuid;
	private boolean pinned;
	private Instant createdAt;
	private boolean updated;
	private List<String> imageUrls;
	private Integer likeCount;
	private Integer commentCount;
	private String writerName;
	private String writerProfileImageUrl;
	private boolean hostStatus;
}
