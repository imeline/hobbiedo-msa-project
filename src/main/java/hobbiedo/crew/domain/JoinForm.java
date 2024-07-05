package hobbiedo.crew.domain;

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
@RedisHash(value = "JoinForm", timeToLive = 864000) // 10일
public class JoinForm {
	@Id
	private String id;
	@Indexed
	private long crewId;
	@Indexed
	private String uuid;
	private String joinMessage;
	private String profileUrl;
	private String name;
	private String birthday;
	private String address;
	private String gender;
	private LocalDateTime createdAt;

	@Builder
	public JoinForm(String id, long crewId, String uuid, String joinMessage, String profileUrl,
		String name, String birthday, String address, String gender, LocalDateTime createdAt) {
		this.id = id;
		this.crewId = crewId;
		this.uuid = uuid;
		this.joinMessage = joinMessage;
		this.profileUrl = profileUrl;
		this.name = name;
		this.birthday = birthday;
		this.address = address;
		this.gender = gender;
		this.createdAt = createdAt;
	}
}
