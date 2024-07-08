package hobbiedo.member.kafka.dto;

import hobbiedo.member.domain.MemberProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDTO {
	private String uuid;
	private String name;

	public MemberProfile toEntity() {
		return MemberProfile.builder()
			.uuid(uuid)
			.name(name)
			.profileUrl(
				"https://hobbiedo-bucket.s3.ap-northeast-2.amazonaws.com/image_1718266148717_Frame%201000004039.png")
			.build();
	}
}
