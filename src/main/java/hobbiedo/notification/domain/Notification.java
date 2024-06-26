package hobbiedo.notification.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "Notification", timeToLive = 864000) // 10일
public class Notification {
	@Id
	private String id;
	@Indexed
	private String uuid;
	private String content;
	private String crewProfileUrl;
	private LocalDateTime createdAt;

	@Builder
	public Notification(String id, String uuid, String content, String crewProfileUrl, LocalDateTime createdAt) {
		this.id = id;
		this.uuid = uuid;
		this.content = content;
		this.crewProfileUrl = crewProfileUrl;
		this.createdAt = createdAt;
	}
}
