package hobbiedo.chat.application;

import java.util.List;

import hobbiedo.chat.dto.response.ChatHistoryListDTO;
import hobbiedo.chat.dto.response.ChatImageListDTO;

public interface ChatService {

	List<ChatHistoryListDTO> getChatHistoryBefore(Long crewId, int page);

	List<ChatImageListDTO> getChatsWithImageUrl(Long crewId);

	void deleteOldChatsWithImageUrl();
}
