package hobbiedo.chat.application;

import java.time.Instant;

import hobbiedo.chat.domain.Chat;
import hobbiedo.chat.vo.request.ChatSendVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatService {

	Mono<Chat> sendChat(ChatSendVo chatSendVo, String uuid);

	//Flux<Chat> getChatByCrewIdAfterDateTime(Long crewId, LocalDateTime since);
	//Flux<Chat> getStreamChatByCrewId(Long crewId);

	Flux<Chat> getChatByCrewIdAfterDateTime(Long crewId, Instant since);
}
