package hobbiedo.user.auth.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyProfileDTO {
	private String uuid;
	private String profileUrl;

	public static ModifyProfileDTO toDto(String uuid, String profileUrl) {
		return ModifyProfileDTO.builder()
			.uuid(uuid)
			.profileUrl(profileUrl)
			.build();
	}
}
