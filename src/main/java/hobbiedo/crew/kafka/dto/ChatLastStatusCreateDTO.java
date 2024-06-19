package hobbiedo.crew.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatLastStatusCreateDTO {
	private long crewId;
	private String uuid;

	public static ChatLastStatusCreateDTO toDto(long crewId, String uuid) {
		return ChatLastStatusCreateDTO.builder()
			.crewId(crewId)
			.uuid(uuid)
			.build();
	}
}
