package hobbiedo.chat.application;

import java.util.List;

import hobbiedo.chat.dto.response.ChatHistoryListDTO;
import hobbiedo.chat.dto.response.ChatImageListDTO;
import hobbiedo.chat.dto.response.ChatListDTO;

public interface ChatService {

	List<ChatHistoryListDTO> getChatHistoryBefore(Long crewId, String uuid, int page);

	List<ChatListDTO> getChatList(String uuid);

	List<ChatImageListDTO> getChatsWithImageUrl(Long crewId);

	void deleteOldChatsWithImageUrl();
}
