package hobbiedo.global.config.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import hobbiedo.member.kafka.dto.ModifyProfileDTO;
import hobbiedo.member.kafka.dto.SignUpDTO;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaMemberConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	/**
	 * Consumer 설정
	 * @return Map<String, Object>
	 */
	private Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // 모든 패키지를 신뢰하도록 설정
		return props;
	}

	/**
	 * 회원 가입 이벤트 ConsumerFactory - memberProfile 생성
	 * @return ConsumerFactory<String, SignUpDTO>
	 */
	@Bean
	public ConsumerFactory<String, SignUpDTO> signUpConsumerFactory() {

		Map<String, Object> configProps = consumerConfigs();

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(SignUpDTO.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, SignUpDTO> signUpKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, SignUpDTO> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(signUpConsumerFactory());

		return factory;
	}

	/**
	 * 프로필 수정 ConsumerFactory - memberProfile 수정
	 * @return ConsumerFactory<String, ModifyProfileDTO>
	 */
	@Bean
	public ConsumerFactory<String, ModifyProfileDTO> modifyProfileConsumerFactory() {

		Map<String, Object> configProps = consumerConfigs();

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(ModifyProfileDTO.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ModifyProfileDTO>
		modifyProfileKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, ModifyProfileDTO> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(modifyProfileConsumerFactory());

		return factory;
	}
}
