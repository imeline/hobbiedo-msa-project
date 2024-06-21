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

import hobbiedo.board.kafka.dto.BoardCommentCountUpdateDto;
import hobbiedo.board.kafka.dto.BoardCreateEventDto;
import hobbiedo.board.kafka.dto.BoardDeleteEventDto;
import hobbiedo.board.kafka.dto.BoardLikeCountUpdateDto;
import hobbiedo.board.kafka.dto.BoardPinEventDto;
import hobbiedo.board.kafka.dto.BoardUnPinEventDto;
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
	 * 게시글 테이블 생성 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardCreateEventDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardCreateEventDto> createConsumerFactory() {

		Map<String, Object> configProps = consumerConfigs();

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

		Map<String, Object> configProps = consumerConfigs();

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
	 * 게시글 테이블 수정 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardUpdateEventDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardUpdateEventDto> updateConsumerFactory() {

		Map<String, Object> configProps = consumerConfigs();

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

	/**
	 * 게시글 테이블 고정 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardPinEventDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardPinEventDto> pinConsumerFactory() {

		Map<String, Object> configProps = consumerConfigs();

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardPinEventDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BoardPinEventDto> pinKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardPinEventDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(pinConsumerFactory());

		return factory;
	}

	/**
	 * 게시글 테이블 고정 해제 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardUnPinEventDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardUnPinEventDto> unpinConsumerFactory() {

		Map<String, Object> configProps = consumerConfigs();

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardUnPinEventDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BoardUnPinEventDto> unpinKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardUnPinEventDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(unpinConsumerFactory());

		return factory;
	}

	/**
	 * 게시글 테이블 댓글 수 증가 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardCommentCountUpdateDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardCommentCountUpdateDto> commentCountUpdateConsumerFactory() {

		Map<String, Object> configProps = consumerConfigs();

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardCommentCountUpdateDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BoardCommentCountUpdateDto> commentCountUpdateKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardCommentCountUpdateDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(commentCountUpdateConsumerFactory());

		return factory;
	}

	/**
	 * 게시글 댓글 수 감소 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardCommentCountUpdateDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardCommentCountUpdateDto> commentCountDeleteConsumerFactory() {

		Map<String, Object> configProps = consumerConfigs();

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardCommentCountUpdateDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BoardCommentCountUpdateDto> commentCountDeleteKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardCommentCountUpdateDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(commentCountDeleteConsumerFactory());

		return factory;
	}

	/**
	 * 게시글 좋아요 수 증가 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardLikeCountUpdateDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardLikeCountUpdateDto> likeCountUpdateConsumerFactory() {

		Map<String, Object> configProps = consumerConfigs();

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardLikeCountUpdateDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BoardLikeCountUpdateDto> likeCountUpdateKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardLikeCountUpdateDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(likeCountUpdateConsumerFactory());

		return factory;
	}

	/**
	 * 게시글 좋아요 수 감소 이벤트용 ConsumerFactory
	 * @return ConsumerFactory<String, BoardLikeCountUpdateDto>
	 */
	@Bean
	public ConsumerFactory<String, BoardLikeCountUpdateDto> likeCountDeleteConsumerFactory() {

		Map<String, Object> configProps = consumerConfigs();

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
			new JsonDeserializer<>(BoardLikeCountUpdateDto.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BoardLikeCountUpdateDto> likeCountDeleteKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, BoardLikeCountUpdateDto> factory =
			new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(likeCountDeleteConsumerFactory());

		return factory;
	}
}
