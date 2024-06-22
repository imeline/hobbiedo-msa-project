package hobbiedo.global.config;

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

import hobbiedo.batch.kafka.dto.consumer.BoardCommentDeleteDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCommentUpdateDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCreateEventDto;
import hobbiedo.batch.kafka.dto.consumer.BoardDeleteEventDto;
import hobbiedo.batch.kafka.dto.consumer.BoardLikeUpdateDto;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	/**
	 * 게시글 통계 테이블 생성 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardCreateEventDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardCreateEventDto> createConsumerFactory() {

		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // 모든 패키지를 신뢰하도록 설정

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
	 * 게시글 통계 테이블 삭제 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardDeleteEventDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardDeleteEventDto> deleteConsumerFactory() {

		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // 모든 패키지를 신뢰하도록 설정

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
	 * 게시글 통계 테이블 댓글 수 증가 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardCommentUpdateDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardCommentUpdateDto> commentUpdateConsumerFactory() {

		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // 모든 패키지를 신뢰하도록 설정

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardCommentUpdateDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String,
		BoardCommentUpdateDto> commentUpdateKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardCommentUpdateDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(commentUpdateConsumerFactory());

		return factory;
	}

	/**
	 * 게시글 통계 테이블 댓글 수 감소 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardCommentDeleteDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardCommentDeleteDto> commentDeleteConsumerFactory() {

		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // 모든 패키지를 신뢰하도록 설정

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardCommentDeleteDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String,
		BoardCommentDeleteDto> commentDeleteKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardCommentDeleteDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(commentDeleteConsumerFactory());

		return factory;
	}

	/**
	 * 게시글 통계 테이블 좋아요 수 증가 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardLikeUpdateDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardLikeUpdateDto> likeUpdateConsumerFactory() {

		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // 모든 패키지를 신뢰하도록 설정

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardLikeUpdateDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String,
		BoardLikeUpdateDto> likeUpdateKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardLikeUpdateDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(likeUpdateConsumerFactory());

		return factory;
	}

	/**
	 * 게시글 통계 테이블 좋아요 수 감소 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardLikeUpdateDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardLikeUpdateDto> likeDeleteConsumerFactory() {

		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // 모든 패키지를 신뢰하도록 설정

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardLikeUpdateDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String,
		BoardLikeUpdateDto> likeDeleteKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardLikeUpdateDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(likeDeleteConsumerFactory());

		return factory;
	}
}
