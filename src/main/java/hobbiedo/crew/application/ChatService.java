package hobbiedo.crew.application;

import java.util.List;

import hobbiedo.crew.dto.response.ChatHistoryListDTO;
import hobbiedo.crew.dto.response.ChatImageListDTO;

public interface ChatService {

	List<ChatHistoryListDTO> getChatHistoryBefore(Long crewId, int page);

	List<ChatImageListDTO> getChatsWithImageUrl(Long crewId);

	void deleteOldChatsWithImageUrl();
}
