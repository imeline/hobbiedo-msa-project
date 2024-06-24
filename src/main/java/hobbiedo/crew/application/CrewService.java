package hobbiedo.crew.application;

import java.util.List;

import hobbiedo.crew.domain.Crew;
import hobbiedo.crew.dto.request.CrewModifyDTO;
import hobbiedo.crew.dto.request.CrewOutDTO;
import hobbiedo.crew.dto.request.CrewRequestDTO;
import hobbiedo.crew.dto.response.CrewDetailDTO;
import hobbiedo.crew.dto.response.CrewHomeDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;
import hobbiedo.crew.dto.response.CrewIdNameDTO;
import hobbiedo.crew.dto.response.CrewNameDTO;
import hobbiedo.crew.dto.response.CrewProfileDTO;
import hobbiedo.crew.dto.response.CrewResponseDTO;
import hobbiedo.crew.kafka.dto.CrewScoreDTO;

public interface CrewService {
	CrewIdDTO createCrew(CrewRequestDTO crewDTO, String uuid);

	void joinCrew(Long crewId, String uuid);

	void joinCrewMember(Crew crew, String uuid);

	List<CrewDetailDTO> getCrewInfoList(long hobbyId, long regionId, String uuid);

	List<CrewProfileDTO> getCrewProfiles(String uuid);

	CrewNameDTO getCrewName(Long crewId);

	void exitCrew(Long crewId, String uuid);

	CrewResponseDTO getCrew(Long crewId);

	void modifyCrew(CrewModifyDTO crewModifyDTO, Long crewId, String uuid);

	void forcedExitCrew(CrewOutDTO crewOutDTO, Long crewId, String uuid);

	List<CrewHomeDTO> getTop5LatestCrews(long hobbyId, long regionId, String uuid);

	List<CrewIdNameDTO> getTop5ScoreCrews(long regionId, String uuid);

	void addCrewScore(CrewScoreDTO crewScoreDTO);

	void minusCrewScore(CrewScoreDTO crewScoreDTO);
}
