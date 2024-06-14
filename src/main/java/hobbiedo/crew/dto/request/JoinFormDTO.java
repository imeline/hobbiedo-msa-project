package hobbiedo.crew.dto.request;

import hobbiedo.crew.domain.JoinForm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class JoinFormDTO {
	@NotBlank
	private String joinMessage;

	@NotBlank
	private String profileUrl;

	@NotBlank
	private String name;

	@NotBlank
	@Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
		message = "생일은 yyyy-MM-dd 형식으로 입력해주세요.")
	private String birthday;

	@NotBlank
	private String address;

	@NotBlank
	@Pattern(regexp = "[남여]", message = "성별은 남 또는 여로 입력해주세요.")
	private String gender;

	public JoinForm toEntity(Long crewId, String uuid) {
		return JoinForm.builder()
			.crewId(crewId)
			.uuid(uuid)
			.joinMessage(joinMessage)
			.profileUrl(profileUrl)
			.name(name)
			.birthday(birthday)
			.address(address)
			.gender(gender)
			.build();
	}
}
