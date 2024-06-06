package hobbiedo.survey.application;

import java.util.List;

import hobbiedo.survey.dto.request.HobbySurveyRequestDto;
import hobbiedo.survey.dto.response.HobbySurveyResponseDto;

public interface SurveyService {

	List<HobbySurveyResponseDto> getHobbySurveyQuestions();

	void saveOrUpdateHobbyData(String uuid, List<HobbySurveyRequestDto> hobbySurveyRequestDtoList);
}
