package hobbiedo.board.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

@Document(collection = "replica_board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplicaBoard {

	@Id
	private String id;
	private Long boardId;
	private Long crewId;
	private String content;
	private String writerUuid;
	private boolean pinned;
	private LocalDateTime createdAt;
	private boolean updated;
	private List<String> imageUrls;
	private Integer likeCount;
	private Integer commentCount;
	private String profileImageUrl;
	private String writerName;

	@Builder
	public ReplicaBoard(String id, Long boardId, Long crewId, String content, String writerUuid,
		boolean pinned,
		LocalDateTime createdAt, boolean updated, List<String> imageUrls, Integer likeCount,
		Integer commentCount,
		String profileImageUrl, String writerName) {
		this.id = id;
		this.boardId = boardId;
		this.crewId = crewId;
		this.content = content;
		this.writerUuid = writerUuid;
		this.pinned = pinned;
		this.createdAt = createdAt;
		this.updated = updated;
		this.imageUrls = imageUrls;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.profileImageUrl = profileImageUrl;
		this.writerName = writerName;
	}
}
