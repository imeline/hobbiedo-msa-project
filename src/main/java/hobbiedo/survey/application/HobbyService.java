package hobbiedo.survey.application;

import java.util.List;

import hobbiedo.survey.dto.response.GetUserHobbyDto;

public interface HobbyService {

	List<GetUserHobbyDto> getUserHobbies(String uuid);
}
