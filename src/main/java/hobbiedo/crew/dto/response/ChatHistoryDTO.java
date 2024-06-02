package hobbiedo.crew.dto.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;

import hobbiedo.crew.domain.Chat;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatHistoryDTO {
	private String uuid;
	private String text;
	private String imageUrl;
	private String entryExitNotice;
	private Instant createdAt;

	public static ChatHistoryDTO toDto(Chat chat) {
		return ChatHistoryDTO.builder()
			.uuid(chat.getUuid())
			.text(chat.getText())
			.imageUrl(chat.getImageUrl())
			.entryExitNotice(chat.getEntryExitNotice())
			.createdAt(chat.getCreatedAt())
			.build();
	}
}
