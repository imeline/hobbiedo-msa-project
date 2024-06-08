package hobbiedo.chat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "UnReadCount")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CompoundIndex(name = "uuid_crewId_idx", def = "{'uuid': 1, 'crewId': 1}")
public class UnReadCount {
	@Id
	private String id;
	private String uuid;
	private Long crewId;
	private Integer unreadCount;

	@Builder
	public UnReadCount(String id, String uuid, Long crewId, Integer unreadCount) {
		this.id = id;
		this.uuid = uuid;
		this.crewId = crewId;
		this.unreadCount = unreadCount;
	}
}
