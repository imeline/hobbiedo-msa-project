package hobbiedo.crew.application;

import java.util.List;

import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.dto.request.CrewOutDTO;
import hobbiedo.crew.dto.request.CrewRequestDTO;
import hobbiedo.crew.dto.response.CrewDetailDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;
import hobbiedo.crew.dto.response.CrewNameDTO;
import hobbiedo.crew.dto.response.CrewProfileDTO;
import hobbiedo.crew.dto.response.CrewResponseDTO;

public interface CrewService {
	CrewIdDTO createCrew(CrewRequestDTO crewDTO, String uuid);

	void joinCrew(Long crewId, String uuid);

	void joinCrewMember(Crew crew, String uuid);

	CrewDetailDTO getCrewInfo(Long crewId);

	List<CrewIdDTO> getCrewsByHobbyAndRegion(long hobbyId, long regionId);

	List<CrewProfileDTO> getCrewProfiles(String uuid);

	CrewNameDTO getCrewName(Long crewId);

	void deleteCrewMember(Long crewId, String uuid);

	CrewResponseDTO getCrew(Long crewId);

	void modifyCrew(CrewRequestDTO crewDTO, Long crewId, String uuid);

	void forcedExitCrew(CrewOutDTO crewOutDTO, Long crewId, String uuid);
}
