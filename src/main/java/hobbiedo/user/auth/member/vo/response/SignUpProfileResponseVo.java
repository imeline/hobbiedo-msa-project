package hobbiedo.user.auth.member.vo.response;

import java.time.LocalDate;

import hobbiedo.user.auth.member.dto.response.ProfileResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "회원 가입 정보 조회 응답")
public class SignUpProfileResponseVo {

	private String name;
	private String phoneNumber;
	private String email;
	private String gender;
	private LocalDate birth;

	public SignUpProfileResponseVo(String name, String phoneNumber, String email,
		String gender, LocalDate birth) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;

		if (gender.equals("FEMALE")) {

			gender = "여성";
		} else if (gender.equals("MALE")) {

			gender = "남성";
		}

		this.gender = gender;
		this.birth = birth;
	}

	public static SignUpProfileResponseVo profileDtoToSignProfileVo(ProfileResponseDto profileDto) {

		return new SignUpProfileResponseVo(
			profileDto.getName(),
			profileDto.getPhoneNumber(),
			profileDto.getEmail(),
			profileDto.getGender().name(),
			profileDto.getBirth()
		);
	}
}
