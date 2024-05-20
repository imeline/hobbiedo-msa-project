package hobbiedo.user.auth.user.domain;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@RedisHash
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {
	@Id
	private String id;
	private String refresh;
	@TimeToLive(unit = TimeUnit.MILLISECONDS)
	private Long expiration;
}
