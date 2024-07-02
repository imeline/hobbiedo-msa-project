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
	private boolean hostStatus;

	@Builder
	public CrewMember(String uuid, boolean hostStatus) {
		this.uuid = uuid;
		this.hostStatus = hostStatus;
	}
}
