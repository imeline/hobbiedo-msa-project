package hobbiedo.chat.dto.response;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatListDTO {
	private Long crewId;
	private String lastChatContent;
	private Integer unreadCount;
	private Instant createdAt;

	public static ChatListDTO toDto(Long crewId, String lastChatContent, Integer unreadCount, Instant createdAt) {
		return ChatListDTO.builder()
			.crewId(crewId)
			.lastChatContent(lastChatContent)
			.unreadCount(unreadCount)
			.createdAt(createdAt)
			.build();
	}
}
