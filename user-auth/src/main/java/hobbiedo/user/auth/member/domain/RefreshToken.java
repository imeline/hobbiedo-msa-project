package hobbiedo.user.auth.member.domain;

import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@RedisHash(value = "refresh")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {
	@Id
	private String uuid;
	@Indexed
	private String refresh;
	@TimeToLive(unit = TimeUnit.MILLISECONDS)
	private Long expiration;
}
