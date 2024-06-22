package hobbiedo.user.auth.kafka.dto;

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

	public static SignUpDTO toDto(String uuid, String name) {
		return SignUpDTO.builder()
			.uuid(uuid)
			.name(name)
			.build();
	}
}
