package hobbiedo.crew.application;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import hobbiedo.crew.dto.request.CrewOutDTO;
import hobbiedo.crew.dto.request.CrewRequestDTO;
import hobbiedo.crew.dto.request.JoinFormRequestDTO;
import hobbiedo.crew.dto.response.CrewDetailDTO;
import hobbiedo.crew.dto.response.CrewIdDTO;
import hobbiedo.crew.dto.response.CrewNameDTO;
import hobbiedo.crew.dto.response.CrewProfileDTO;
import hobbiedo.crew.dto.response.CrewResponseDTO;
import hobbiedo.crew.dto.response.JoinFormListDTO;
import hobbiedo.crew.dto.response.JoinFormResponseDTO;
import hobbiedo.crew.dto.response.MyJoinFormDTO;

public interface CrewService {
	CrewIdDTO createCrew(CrewRequestDTO crewDTO, String uuid);

	void joinCrew(Long crewId, String uuid);

	CrewDetailDTO getCrewInfo(Long crewId);

	List<CrewIdDTO> getCrewsByHobbyAndRegion(long hobbyId, long regionId);

	void addJoinForm(JoinFormRequestDTO joinFormDTO, Long crewId, String uuid);

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

	JoinFormResponseDTO getJoinForm(String joinFormId);

	@Transactional
	void acceptJoinForm(String joinFormId, String uuid);

	@Transactional
	void rejectJoinForm(String joinFormId, String uuid);

	List<MyJoinFormDTO> getMyJoinForms(String uuid);

	@Transactional
	void cancelJoinForm(String joinFormId, String uuid);
}
