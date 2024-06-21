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

import hobbiedo.board.kafka.dto.BoardCreateEventDto;
import hobbiedo.board.kafka.dto.BoardDeleteEventDto;
import hobbiedo.board.kafka.dto.BoardUpdateEventDto;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaBoardConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	/**
	 * 게시글 테이블 생성 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardCreateEventDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardCreateEventDto> createConsumerFactory() {

		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardCreateEventDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BoardCreateEventDto> createKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardCreateEventDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(createConsumerFactory());

		return factory;
	}

	/**
	 * 게시글 테이블 삭제 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardDeleteEventDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardDeleteEventDto> deleteConsumerFactory() {

		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardDeleteEventDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BoardDeleteEventDto> deleteKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardDeleteEventDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(deleteConsumerFactory());

		return factory;
	}

	/**
	 * 게시글 테이블 댓글 증가 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardUpdateEventDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardUpdateEventDto> updateConsumerFactory() {

		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardUpdateEventDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BoardUpdateEventDto> updateKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardUpdateEventDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(updateConsumerFactory());

		return factory;
	}
}
