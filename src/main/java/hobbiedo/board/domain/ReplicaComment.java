package hobbiedo.board.domain;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

@Document(collection = "replica_comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplicaComment {

	@Id
	private String id;
	private Long boardId; // 게시글 번호
	private Long commentId; // 댓글 번호
	private String writerUuid; // 작성자 uuid
	private String content; // 댓글 내용
	private Boolean isInCrew; // 소모임 회원 여부
	private Instant createdAt; // 작성일

	@Builder
	public ReplicaComment(String id, Long boardId, Long commentId, String writerUuid,
		String content, Boolean isInCrew, Instant createdAt) {
		this.id = id;
		this.boardId = boardId;
		this.commentId = commentId;
		this.writerUuid = writerUuid;
		this.content = content;
		this.isInCrew = isInCrew;
		this.createdAt = createdAt;
	}
}
