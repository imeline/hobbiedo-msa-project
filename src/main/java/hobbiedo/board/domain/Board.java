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
	private int likeCount;

	// 댓글 개수, default = 0
	@Column(nullable = false)
	private int commentCount;

	// 고정 여부, default = false
	@Column(nullable = false)
	private boolean isPinned;

	@Builder
	public Board(String title, String content, String writerUuid, Long crewId, int likeCount,
			int commentCount, boolean isPinned) {
		this.title = title;
		this.content = content;
		this.writerUuid = writerUuid;
		this.crewId = crewId;
		this.likeCount = 0; // 좋아요 개수는 0으로 초기화
		this.commentCount = 0; // 댓글 개수는 0으로 초기화
		this.isPinned = false; // 고정 여부는 false 로 초기화
	}
}
