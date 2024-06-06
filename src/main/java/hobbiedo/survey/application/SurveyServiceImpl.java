package hobbiedo.survey.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.global.api.exception.handler.SurveyExceptionHandler;
import hobbiedo.survey.domain.HobbySurvey;
import hobbiedo.survey.dto.response.HobbySurveyResponseDto;
import hobbiedo.survey.infrastructure.HobbySurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyServiceImpl implements SurveyService {

	private final HobbySurveyRepository hobbySurveyRepository;

	@Override
	public List<HobbySurveyResponseDto> getHobbySurveyQuestions() {

		List<HobbySurvey> randomQuestions = Optional.ofNullable(
				hobbySurveyRepository.findRandomQuestions())
			.filter(list -> !list.isEmpty())
			.orElseThrow(() -> new SurveyExceptionHandler(
				GET_HOBBY_SURVEY_QUESTIONS_EMPTY));

		log.info("Get Hobby Survey Questions Size: {}", randomQuestions.size());

		if (randomQuestions.size() < 20) {
			throw new SurveyExceptionHandler(GET_HOBBY_SURVEY_QUESTIONS_LESS);
		}

		List<HobbySurveyResponseDto> getHobbySurveyDtoList = randomQuestions.stream()
			.map(HobbySurveyResponseDto::hobbySurveyToDto)
			.toList();

		return getHobbySurveyDtoList;
	}
}
