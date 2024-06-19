package hobbiedo.crew.kafka.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import hobbiedo.crew.kafka.dto.ChatLastStatusCreateDTO;
import hobbiedo.crew.kafka.dto.EntryExitDTO;
import hobbiedo.global.exception.GlobalException;
import hobbiedo.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private static final String CREATE_CHAT_STATUS_TOPIC = "create-chat-status-topic";
	private static final String ENTRY_EXIT_CHAT_TOPIC = "entry-exit-chat-topic";

	// ChatLastStatus 생성
	public void createChatLastStatus(ChatLastStatusCreateDTO eventDto) {
		try {

			kafkaTemplate.send(CREATE_CHAT_STATUS_TOPIC, eventDto);

		} catch (Exception e) {

			log.info(e.getMessage());
			throw new GlobalException(ErrorStatus.KAFKA_SEND_ERROR, e.getMessage());
		}
	}

	// 입퇴장 안내 채팅 전송
	public void sendEntryExitChat(EntryExitDTO eventDto) {
		try {

			kafkaTemplate.send(ENTRY_EXIT_CHAT_TOPIC, eventDto);

		} catch (Exception e) {

			log.info(e.getMessage());
			throw new GlobalException(ErrorStatus.KAFKA_SEND_ERROR, e.getMessage());
		}
	}
}
