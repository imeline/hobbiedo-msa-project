package hobbiedo.crew.application;

import hobbiedo.crew.dto.request.CrewDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;

public interface CrewService {
	CrewIdDTO createCrew(CrewDTO crewDTO, String uuid);

	void joinCrew(Long crewId, String uuid);
}
