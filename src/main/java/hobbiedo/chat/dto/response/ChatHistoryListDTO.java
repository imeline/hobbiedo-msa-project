package hobbiedo.chat.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatHistoryListDTO {
	private LocalDate date;
	private List<ChatHistoryDTO> chats;

	public static ChatHistoryListDTO toDto(LocalDate date, List<ChatHistoryDTO> chats) {
		return ChatHistoryListDTO.builder()
			.date(date)
			.chats(chats)
			.build();
	}
}
