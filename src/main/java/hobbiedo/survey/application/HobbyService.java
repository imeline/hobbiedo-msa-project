package hobbiedo.survey.application;

import java.util.List;

import hobbiedo.survey.dto.response.UserHobbyResponseDto;

public interface HobbyService {

	List<UserHobbyResponseDto> getUserHobbies(String uuid);
}
