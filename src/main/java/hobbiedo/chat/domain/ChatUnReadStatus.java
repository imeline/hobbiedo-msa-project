package hobbiedo.chat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "ChatUnReadStatus")
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatUnReadStatus {
	@Id
	private String id;
	private String uuid;
	private Long crewId;
	private String lastReadChatId;
	private Integer unreadChatCount;

}
