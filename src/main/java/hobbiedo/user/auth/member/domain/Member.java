package hobbiedo.user.auth.member.domain;

import java.time.LocalDate;

import hobbiedo.user.auth.global.base.BaseEntity;
import hobbiedo.user.auth.member.type.GenderType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member extends BaseEntity {
	public static final String BLANK = "";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String uuid;

	private String email;

	private String phoneNumber;

	private Boolean active;

	@Enumerated(EnumType.STRING)
	private GenderType gender;

	private LocalDate birth;

	@Builder.Default
	private String profileMessage = BLANK;

	@Builder
	public Member(String name, String uuid, String email, String phoneNumber, Boolean active, GenderType gender,
		LocalDate birth, String profileMessage) {
		this.name = name;
		this.uuid = uuid;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.active = active;
		this.gender = gender;
		this.birth = birth;
		this.profileMessage = profileMessage;
	}
}
