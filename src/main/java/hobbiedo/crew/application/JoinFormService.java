package hobbiedo.crew.application;

import java.util.List;

import hobbiedo.crew.dto.request.JoinFormRequestDTO;
import hobbiedo.crew.dto.response.JoinFormListDTO;
import hobbiedo.crew.dto.response.JoinFormResponseDTO;
import hobbiedo.crew.dto.response.MyJoinFormDTO;

public interface JoinFormService {
	void addJoinForm(JoinFormRequestDTO joinFormDTO, Long crewId, String uuid);

	List<JoinFormListDTO> getJoinFormList(Long crewId, String uuid);

	JoinFormResponseDTO getJoinForm(String joinFormId);

	void acceptJoinForm(String joinFormId, String uuid);

	void rejectJoinForm(String joinFormId, String uuid);

	List<MyJoinFormDTO> getMyJoinForms(String uuid);

	void cancelJoinForm(String joinFormId, String uuid);
}
