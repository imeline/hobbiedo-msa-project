package hobbiedo.user.auth.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import hobbiedo.user.auth.member.domain.RefreshToken;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
	@Bean
	public RedisTemplate<String, RefreshToken> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, RefreshToken> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}
}
