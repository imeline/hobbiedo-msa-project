package hobbiedo.crew.application;

import java.time.Instant;
import java.util.List;

import hobbiedo.crew.dto.response.ChatHistoryListDTO;
import hobbiedo.crew.dto.response.ChatImageListDTO;

public interface ChatService {
	List<ChatHistoryListDTO> getChatHistoryBefore(Long crewId, Instant oldestChatTime);

	List<ChatImageListDTO> getChatsWithImageUrl(Long crewId);

	void deleteOldChatsWithImageUrl();
}
