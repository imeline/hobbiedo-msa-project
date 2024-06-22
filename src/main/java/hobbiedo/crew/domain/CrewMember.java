package hobbiedo.crew.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrewMember {

	private String uuid;
	private String name;
	private String profileUrl;
	private boolean hostStatus;

	@Builder
	public CrewMember(String uuid, String name, String profileUrl, boolean hostStatus) {
		this.uuid = uuid;
		this.name = name;
		this.profileUrl = profileUrl;
		this.hostStatus = hostStatus;
	}
}
