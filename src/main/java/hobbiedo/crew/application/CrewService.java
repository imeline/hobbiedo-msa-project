package hobbiedo.crew.application;

import hobbiedo.crew.dto.request.CrewRequestDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;
import hobbiedo.crew.dto.response.CrewResponseDTO;

public interface CrewService {
	CrewIdDTO createCrew(CrewRequestDTO crewDTO, String uuid);

	void joinCrew(Long crewId, String uuid);

	CrewResponseDTO getCrewInfo(Long crewId);
}
