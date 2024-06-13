package hobbiedo.crew.domain;

import org.hibernate.annotations.ColumnDefault;

import hobbiedo.global.base.BaseTime;
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
public class Crew extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long regionId;

	@Column(nullable = false)
	private Long hobbyId;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false, length = 250)
	private String introduction;

	@Column(nullable = false)
	private int currentParticipant;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private int joinType;  // 0:자유, 1: 신청

	@Column(nullable = false)
	@ColumnDefault("")
	private String profileUrl;

	@Column(nullable = false)
	@ColumnDefault("0")
	private int score;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	@ColumnDefault("1")
	private boolean active;

	@Builder
	public Crew(Long id, Long regionId, Long hobbyId, String name, String introduction,
		int currentParticipant, int joinType, String profileUrl, int score, boolean active) {
		this.id = id;
		this.regionId = regionId;
		this.hobbyId = hobbyId;
		this.name = name;
		this.introduction = introduction;
		this.currentParticipant = currentParticipant;
		this.joinType = joinType;
		this.profileUrl = profileUrl;
		this.score = score;
		this.active = active;
	}
}
