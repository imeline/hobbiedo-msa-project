package hobbiedo.crew.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "replica_crew")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Crew {
	@Id
	private String id;
	private long crewId;
	private List<CrewMember> crewMembers;

	@Builder
	public Crew(String id, long crewId, List<CrewMember> crewMembers) {
		this.id = id;
		this.crewId = crewId;
		this.crewMembers = crewMembers;
	}
}
