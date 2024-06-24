package hobbiedo.chat.application.reactive;

import java.time.Instant;

import hobbiedo.chat.dto.request.ChatSendDTO;
import hobbiedo.chat.dto.request.LastStatusModifyDTO;
import hobbiedo.chat.dto.response.ChatStreamDTO;
import hobbiedo.chat.dto.response.LastChatInfoDTO;
import hobbiedo.chat.kafka.dto.ChatEntryExitDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveChatService {

	Mono<Void> sendChat(ChatSendDTO chatSendDTO, String uuid);

	Flux<ChatStreamDTO> getStreamChat(Long crewId, String uuid);

	Mono<LastChatInfoDTO> getOneLatestChat(Long crewId, String uuid);

	Flux<LastChatInfoDTO> getStreamLatestChat(Long crewId, String uuid,
		Instant latestChatCreatedAt);

	Mono<Void> updateLastStatusAt(LastStatusModifyDTO lastStatusModifyDTO, String uuid);

	Mono<Void> createEntryChatAndJoinTime(ChatEntryExitDTO entryExitDTO);

	Mono<Void> createExitChatAndDeleteJoinTime(ChatEntryExitDTO entryExitDTO);
}
