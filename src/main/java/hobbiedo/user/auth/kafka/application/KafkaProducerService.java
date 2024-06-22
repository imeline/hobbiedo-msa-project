package hobbiedo.user.auth.kafka.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import hobbiedo.user.auth.global.api.code.status.ErrorStatus;
import hobbiedo.user.auth.global.exception.MemberExceptionHandler;
import hobbiedo.user.auth.kafka.dto.ModifyProfileDTO;
import hobbiedo.user.auth.kafka.dto.SignUpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private static final String SIGN_UP_TOPIC = "sign-up-topic";
	private static final String UPDATE_PROFILE_TOPIC = "update-profile-topic";

	public void setSignUpTopic(SignUpDTO eventDto) {
		try {

			kafkaTemplate.send(SIGN_UP_TOPIC, eventDto);

		} catch (Exception e) {

			log.info(e.getMessage());
			throw new MemberExceptionHandler(ErrorStatus.KAFKA_SEND_ERROR);
		}
	}

	public void setUpdateProfileTopic(ModifyProfileDTO eventDto) {
		try {

			kafkaTemplate.send(UPDATE_PROFILE_TOPIC, eventDto);

		} catch (Exception e) {

			log.info(e.getMessage());
			throw new MemberExceptionHandler(ErrorStatus.KAFKA_SEND_ERROR);
		}
	}
}
