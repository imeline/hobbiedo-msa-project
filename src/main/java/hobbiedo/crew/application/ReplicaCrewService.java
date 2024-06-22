package hobbiedo.crew.application;

import java.util.List;

import hobbiedo.crew.dto.response.CrewMemberDTO;
import hobbiedo.crew.kafka.dto.CrewEntryExitDTO;

public interface ReplicaCrewService {
	List<CrewMemberDTO> getCrewMembers(long crewId);

	void createCrew(CrewEntryExitDTO crewEntryExitDTO);

	void addCrewMember(CrewEntryExitDTO crewEntryExitDTO);

	void deleteCrewMember(CrewEntryExitDTO crewEntryExitDTO);
}
