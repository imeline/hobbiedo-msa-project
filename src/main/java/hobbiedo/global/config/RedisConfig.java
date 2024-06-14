package hobbiedo.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories(basePackages = "hobbiedo.crew.infrastructure.redis")
public class RedisConfig {
	@Bean
	public RedisTemplate<String, Object> redisTemplate(
		RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}

	// @Value("${spring.data.redis.host}")
	// private String host;
	//
	// @Value("${spring.data.redis.port}")
	// private int port;
	//
	// @Value("${spring.data.redis.password}")
	// private String password;
	//
	// @Bean
	// public RedisConnectionFactory redisConnectionFactory() {
	// 	RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
	// 	redisConfig.setHostName(host);
	// 	redisConfig.setPort(port);
	// 	redisConfig.setPassword(password);
	// 	return new LettuceConnectionFactory(redisConfig);
	// }
	//
	// @Bean
	// public RedisTemplate<String, Object> redisTemplate() {
	// 	RedisTemplate<String, Object> template = new RedisTemplate<>();
	// 	template.setConnectionFactory(redisConnectionFactory());
	// 	template.setKeySerializer(new StringRedisSerializer());
	// 	template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
	// 	template.setHashKeySerializer(new StringRedisSerializer());
	// 	template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
	// 	return template;
	// }
}
