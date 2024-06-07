package hobbiedo.region.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String uuid;

	@NotNull
	@Column(nullable = false)
	private Integer currentSelectedRange;

	@NotNull
	@Column(nullable = false)
	private double latitude;

	@NotNull
	@Column(nullable = false)
	private double longitude;

	@NotBlank
	@Column(nullable = false)
	private String addressName;

	@NotNull
	@Column(nullable = false)
	private String legalCode;

	@NotNull
	@Column(nullable = false)
	private boolean isBaseRegion;

}
