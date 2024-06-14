package hobbiedo.crew.domain;

import org.hibernate.annotations.ColumnDefault;

import hobbiedo.global.base.BaseTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrewMember extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "crew_id", nullable = false)
	private Crew crew;

	@Column(nullable = false, length = 50)
	private String uuid;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private int role;  // 0: 일반, 1: 방장

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	@ColumnDefault("0")
	private boolean banned;  // 0: 정상, 1: 블랙리스트

	@Builder
	public CrewMember(Long id, Crew crew, String uuid, int role, boolean banned) {
		this.id = id;
		this.crew = crew;
		this.uuid = uuid;
		this.role = role;
		this.banned = banned;
	}
}
