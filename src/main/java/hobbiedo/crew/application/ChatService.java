package hobbiedo.crew.application;

import java.time.Instant;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.crew.dto.response.ChatHistoryListDTO;

public interface ChatService {
	List<ChatHistoryListDTO> getChatHistorySince(Long crewId, Instant since);


	void deleteOldChatsWithImageUrl();
}
