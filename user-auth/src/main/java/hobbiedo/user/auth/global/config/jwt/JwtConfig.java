package hobbiedo.user.auth.global.config.jwt;

import static hobbiedo.user.auth.global.config.jwt.TokenType.*;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class JwtConfig {
	private final Environment environment;

	@PostConstruct
	public void init() {
		Long accessExpireTime = Long.parseLong(
				Objects.requireNonNull(environment.getProperty("jwt.access-expire-time")));
		Long refreshExpireTime = Long.parseLong(
				Objects.requireNonNull(environment.getProperty("jwt.refresh-expire-time")));

		ACCESS_TOKEN.setExpireTime(accessExpireTime);
		REFRESH_TOKEN.setExpireTime(refreshExpireTime);
	}

	@Bean
	public SecretKey secretKey() {
		String secret = Objects.requireNonNull(environment.getProperty("jwt.secret-key"));
		String signature = Jwts.SIG
				.HS256
				.key()
				.build()
				.getAlgorithm();

		return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), signature);
	}
}
