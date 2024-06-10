package hobbiedo.board.domain;

import hobbiedo.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 게시글 제목
	@Column(nullable = false, length = 255)
	private String title;

	// 게시글 내용
	@Column(nullable = false, length = 3000)
	@Size(max = 1001, message = "게시글 내용은 반드시 1000자 이내여야 합니다")
	private String content;

	// 작성자
	@Column(nullable = false, length = 255)
	private String writerUuid;

	// 소모임 id
	@Column(nullable = false)
	private Long crewId;

	// 좋아요 개수, default = 0
	@Column(nullable = false)
	private Long likeCount;

	// 댓글 개수, default = 0
	@Column(nullable = false)
	private Long commentCount;

	// 고정 여부, default = false
	@Column(nullable = false)
	private boolean isPinned;

	// 수정 여부, default = false
	@Column(nullable = false)
	private boolean isUpdated;

	@Builder
	public Board(String title, String content, String writerUuid, Long crewId, Long likeCount,
			Long commentCount, boolean isPinned, boolean isUpdated) {
		this.title = title;
		this.content = content;
		this.writerUuid = writerUuid;
		this.crewId = crewId;
		this.likeCount = 0L;
		this.commentCount = 0L;
		this.isPinned = false;
		this.isUpdated = false;
	}
}
