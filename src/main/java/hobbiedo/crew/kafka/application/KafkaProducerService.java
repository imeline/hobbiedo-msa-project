package hobbiedo.crew.kafka.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import hobbiedo.crew.kafka.dto.ChatEntryExitDTO;
import hobbiedo.global.exception.GlobalException;
import hobbiedo.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private static final String CREATE_CREW_TOPIC = "create-crew-topic";
	private static final String JOIN_CREW_TOPIC = "join-crew-topic";
	private static final String EXIT_CREW_TOPIC = "exit-crew-topic";
	private static final String FORCE_EXIT_CREW_TOPIC = "force-exit-crew-topic";

	public void setCreateCrewTopic(ChatEntryExitDTO eventDto) {
		try {

			kafkaTemplate.send(CREATE_CREW_TOPIC, eventDto);

		} catch (Exception e) {

			log.info(e.getMessage());
			throw new GlobalException(ErrorStatus.KAFKA_SEND_ERROR, e.getMessage());
		}
	}

	public void setJoinCrewTopic(ChatEntryExitDTO eventDto) {
		try {

			kafkaTemplate.send(JOIN_CREW_TOPIC, eventDto);

		} catch (Exception e) {

			log.info(e.getMessage());
			throw new GlobalException(ErrorStatus.KAFKA_SEND_ERROR, e.getMessage());
		}
	}

	public void setExitCrewTopic(ChatEntryExitDTO eventDto) {
		try {

			kafkaTemplate.send(EXIT_CREW_TOPIC, eventDto);

		} catch (Exception e) {

			log.info(e.getMessage());
			throw new GlobalException(ErrorStatus.KAFKA_SEND_ERROR, e.getMessage());
		}
	}

	public void setForceExitCrewTopic(ChatEntryExitDTO eventDto) {
		try {

			kafkaTemplate.send(FORCE_EXIT_CREW_TOPIC, eventDto);

		} catch (Exception e) {

			log.info(e.getMessage());
			throw new GlobalException(ErrorStatus.KAFKA_SEND_ERROR, e.getMessage());
		}
	}
}
