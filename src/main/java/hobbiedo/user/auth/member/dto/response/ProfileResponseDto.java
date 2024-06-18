package hobbiedo.user.auth.member.dto.response;

import java.time.LocalDate;

import hobbiedo.user.auth.member.type.GenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {

	private String uuid;
	private String name;
	private String email;
	private String phoneNumber;
	private LocalDate birth;
	private GenderType gender;
	private String profileImageUrl;
	private String profileMessage;
}
