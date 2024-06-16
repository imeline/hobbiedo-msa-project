package hobbiedo.batch.domain;

import org.hibernate.annotations.ColumnDefault;

import hobbiedo.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardStats extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long boardId;

	@Column(nullable = false)
	@ColumnDefault("0")
	private Integer likeCount;

	@Column(nullable = false)
	@ColumnDefault("0")
	private Integer commentCount;

	@Builder
	public BoardStats(Long boardId, Integer likeCount, Integer commentCount) {
		this.boardId = boardId;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
	}
}
