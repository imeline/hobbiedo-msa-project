package hobbiedo.crew.application;

import java.util.List;

import hobbiedo.crew.dto.request.CrewRequestDTO;
import hobbiedo.crew.dto.request.JoinFormDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;
import hobbiedo.crew.dto.response.CrewProfileDTO;
import hobbiedo.crew.dto.response.CrewResponseDTO;

public interface CrewService {
	CrewIdDTO createCrew(CrewRequestDTO crewDTO, String uuid);

	void joinCrew(Long crewId, String uuid);

	CrewResponseDTO getCrewInfo(Long crewId);

	List<CrewIdDTO> getCrewsByHobbyAndRegion(long hobbyId, long regionId);

	void addJoinForm(JoinFormDTO joinFormDTO, Long crewId, String uuid);

	List<CrewProfileDTO> getCrewProfiles(String uuid);
}
