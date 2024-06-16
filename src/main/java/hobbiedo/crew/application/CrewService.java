package hobbiedo.crew.application;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import hobbiedo.crew.dto.request.CrewOutDTO;
import hobbiedo.crew.dto.request.CrewRequestDTO;
import hobbiedo.crew.dto.request.JoinFormDTO;
import hobbiedo.crew.dto.response.CrewDetailDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;
import hobbiedo.crew.dto.response.CrewNameDTO;
import hobbiedo.crew.dto.response.CrewProfileDTO;
import hobbiedo.crew.dto.response.CrewResponseDTO;
import hobbiedo.crew.dto.response.JoinFormListDTO;

public interface CrewService {
	CrewIdDTO createCrew(CrewRequestDTO crewDTO, String uuid);

	void joinCrew(Long crewId, String uuid);

	CrewDetailDTO getCrewInfo(Long crewId);

	List<CrewIdDTO> getCrewsByHobbyAndRegion(long hobbyId, long regionId);

	void addJoinForm(JoinFormDTO joinFormDTO, Long crewId, String uuid);

	List<CrewProfileDTO> getCrewProfiles(String uuid);

	CrewNameDTO getCrewName(Long crewId);

	@Transactional
	void deleteCrewMember(Long crewId, String uuid);

	CrewResponseDTO getCrew(Long crewId);

	@Transactional
	void modifyCrew(CrewRequestDTO crewDTO, Long crewId, String uuid);

	@Transactional
	void forcedExitCrew(CrewOutDTO crewOutDTO, Long crewId, String uuid);

	List<JoinFormListDTO> getJoinFormList(Long crewId, String uuid);
}
