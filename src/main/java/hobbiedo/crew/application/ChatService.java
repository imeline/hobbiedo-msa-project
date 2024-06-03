package hobbiedo.crew.application;

import java.time.Instant;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.crew.dto.response.ChatHistoryListDTO;
import hobbiedo.crew.dto.response.ChatImageListDTO;

public interface ChatService {
	List<ChatHistoryListDTO> getChatHistorySince(Long crewId, Instant since);

	List<ChatImageListDTO> getChatsWithImageUrl(Long crewId);

	void deleteOldChatsWithImageUrl();
}
