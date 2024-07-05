package hobbiedo.survey.domain;

import hobbiedo.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHobby extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String uuid;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Hobby hobby;

	@Column(nullable = false)
	private Integer fitRate; // 취미 적합도

	@Builder
	public UserHobby(String uuid, Hobby hobby, Integer fitRate) {
		this.uuid = uuid;
		this.hobby = hobby;
		this.fitRate = fitRate;
	}
}
