package hobbiedo.chat.application;

import org.springframework.stereotype.Service;

import hobbiedo.chat.domain.Chat;
import hobbiedo.chat.dto.request.ChatSendDTO;
import hobbiedo.chat.dto.response.ChatStreamDTO;
import hobbiedo.chat.infrastructure.ChatRepository;
import hobbiedo.chat.infrastructure.ChatUnReadStatusRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ChatServiceImp implements ChatService {
	private final ChatRepository chatRepository;
	private final ChatUnReadStatusRepository chatUnReadStatusRepository;

	@Override
	public Mono<Chat> sendChat(ChatSendDTO chatSendDTO,
		String uuid) { // 응답을 void 로 보내면 error를 잡을 수 없어서 chat으로 return
		return chatRepository.save(chatSendDTO.toEntity(uuid));
	}

	@Override
	public Flux<ChatStreamDTO> getStreamChat(Long crewId, String uuid) {
		return chatUnReadStatusRepository.findByUuidAndCrewId(uuid, crewId)
			.flatMapMany(
				chatUnReadStatus -> chatRepository.findChatByCrewIdAndCreatedAtOrAfter(crewId,
					chatUnReadStatus.getLastReadAt()))
			.map(ChatStreamDTO::toDto);
	}

	// @Override
	// public Flux<LastChatInfoDTO> getLatestChats(String uuid) {
	// 	return chatUnReadStatusRepository.findByUuid(uuid)
	// 		.map(ChatUnReadStatus::getCrewId)
	// 		.flatMap(crewId -> chatRepository.findLatestByCrewId(crewId)
	// 			.flatMapMany(latestChat -> {
	// 				Instant latestCreatedAt = latestChat.getCreatedAt();
	// 				return chatRepository.findChatByCrewIdAndCreatedAtOrAfter(crewId,
	// 						latestCreatedAt)
	// 					.flatMap(chat -> setLastChatContent(chat)  // 비동기적으로 내용 처리
	// 						.map(lastChatContent -> LastChatInfoDTO.toDTO(chat, lastChatContent)));
	// 			})
	// 		);
	// }
	//
	// private Mono<String> setLastChatContent(Chat chat) {
	// 	return Mono.justOrEmpty(chat.getText())
	// 		.switchIfEmpty(
	// 			Mono.justOrEmpty(chat.getImageUrl())
	// 				.map(url -> "사진을 보냈습니다.")
	// 				.switchIfEmpty(Mono.error(new GlobalException(ErrorStatus.NO_CHAT_CONTENT)))
	// 		);
	// }

}
