package hobbiedo.survey.application;

import java.util.List;

import hobbiedo.survey.dto.response.GetHobbySurveyDto;

public interface SurveyService {

	List<GetHobbySurveyDto> getHobbySurveyQuestions();
}
