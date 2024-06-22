package hobbiedo.crew.application;

import hobbiedo.crew.kafka.dto.CrewEntryExitDTO;

public interface ReplicaCrewService {
	void createCrew(CrewEntryExitDTO crewEntryExitDTO);

	void addCrewMember(CrewEntryExitDTO crewEntryExitDTO);

	void deleteCrewMember(CrewEntryExitDTO crewEntryExitDTO);
}
