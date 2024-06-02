package hobbiedo.crew.dto.response;

import java.time.Instant;

import hobbiedo.crew.domain.Chat;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatImageDTO {
	private String uuid;
	private String imageUrl;
	private Instant createdAt;

	public static ChatHistoryDTO toDto(Chat chat) {
		return ChatHistoryDTO.builder()
			.uuid(chat.getUuid())
			.imageUrl(chat.getImageUrl())
			.createdAt(chat.getCreatedAt())
			.build();
	}
}
