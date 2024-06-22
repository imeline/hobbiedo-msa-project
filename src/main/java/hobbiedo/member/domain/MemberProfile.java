package hobbiedo.member.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "replica_profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberProfile {
	@Id
	private String id;
	private String uuid;
	private String name;
	private String profileUrl;

	@Builder
	public MemberProfile(String id, String uuid, String name, String profileUrl) {
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.profileUrl = profileUrl;
	}
}
