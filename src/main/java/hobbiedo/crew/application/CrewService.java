package hobbiedo.crew.application;

import org.springframework.transaction.annotation.Transactional;

import hobbiedo.crew.dto.request.CrewDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;

public interface CrewService {
	CrewIdDTO createCrew(CrewDTO crewDTO, String uuid);
}
