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
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
	public static final String BLANK = "";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String uuid;

	private String email;

	private String phoneNumber;

	@Builder.Default
	private Boolean active = true;

	@Enumerated(EnumType.STRING)
	private GenderType gender;

	private LocalDate birth;

	@Builder.Default
	private String profileMessage = BLANK;

	@Builder.Default
<<<<<<< HEAD
	private String imageUrl = "https://hobbiedo-bucket.s3.ap-northeast-2.amazonaws.com/image_1718266148717_Frame%201000004039.png";
=======
	private String imageUrl = "https://hobbiedo-bucket.s3.ap-northeast-2.amazonaws.com/image_1718266148717_Frame%2010000";
>>>>>>> c24d53a (refactor: 회원 기본 프로필 URI 설정)

}
