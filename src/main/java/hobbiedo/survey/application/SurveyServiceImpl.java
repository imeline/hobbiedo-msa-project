package hobbiedo.survey.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.global.api.exception.handler.SurveyExceptionHandler;
import hobbiedo.survey.domain.Hobby;
import hobbiedo.survey.domain.HobbySurvey;
import hobbiedo.survey.domain.UserHobby;
import hobbiedo.survey.dto.request.HobbySurveyRequestDto;
import hobbiedo.survey.dto.response.HobbySurveyResponseDto;
import hobbiedo.survey.infrastructure.HobbyRepository;
import hobbiedo.survey.infrastructure.HobbySurveyRepository;
import hobbiedo.survey.infrastructure.UserHobbyRepository;
import hobbiedo.survey.type.QuestionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyServiceImpl implements SurveyService {

	private final HobbySurveyRepository hobbySurveyRepository;
	private final UserHobbyRepository userHobbyRepository;
	private final HobbyRepository hobbyRepository;

	@Override
	public List<HobbySurveyResponseDto> getHobbySurveyQuestions() {

		// 데이터베이스에서 랜덤으로 20개의 취미 설문 문항을 가져옴
		List<HobbySurvey> randomQuestions = new ArrayList<>();

		for (QuestionType questionType : QuestionType.values()) {
			List<HobbySurvey> questionsByType = Optional.ofNullable(
					hobbySurveyRepository.findRandomQuestionsByType(questionType.name()))
				.filter(list -> !list.isEmpty())
				.orElseThrow(() -> new SurveyExceptionHandler(GET_HOBBY_SURVEY_QUESTIONS_EMPTY));

			randomQuestions.addAll(questionsByType);
		}

		log.info("Get Hobby Survey Questions Size (취미 설문 조사 질문 수) : {}", randomQuestions.size());

		// 취미 설문 문항이 20개 미만일 경우 예외 처리
		if (randomQuestions.size() < 20) {
			throw new SurveyExceptionHandler(GET_HOBBY_SURVEY_QUESTIONS_LESS);
		}

		// 취미 설문 문항을 DTO로 변환하여 반환
		List<HobbySurveyResponseDto> getHobbySurveyDtoList = randomQuestions.stream()
			.map(HobbySurveyResponseDto::hobbySurveyToDto)
			.toList();

		return getHobbySurveyDtoList;
	}

	@Override
	@Transactional
	public void saveOrUpdateHobbyData(String uuid,
		List<HobbySurveyRequestDto> hobbySurveyRequestDtoList) {

		// 취미 설문에 대한 응답이 20개가 아니거나 비어있을 경우 예외 처리
		if (hobbySurveyRequestDtoList.isEmpty()) {

			throw new SurveyExceptionHandler(HOBBY_SURVEY_QUESTIONS_EMPTY);
		} else if (hobbySurveyRequestDtoList.size() != 20) {

			throw new SurveyExceptionHandler(HOBBY_SURVEY_QUESTIONS_SIZE_LESS_OR_OVER);
		}

		log.info("uuid: {}", uuid);

		// 해당 회원의 취미 데이터가 존재하지 않을 경우 새로 생성, 존재할 경우 업데이트
		List<UserHobby> userHobbies = userHobbyRepository.findByUuid(uuid);

		if (userHobbies.isEmpty()) {
			createUserHobbies(uuid, hobbySurveyRequestDtoList);
		} else {
			updateUserHobbies(uuid, hobbySurveyRequestDtoList);
		}
	}

	/**
	 * 새로운 회원의 취미 데이터를 생성
	 * @param uuid
	 * @param hobbySurveyRequestDtoList
	 */
	private void createUserHobbies(String uuid,
		List<HobbySurveyRequestDto> hobbySurveyRequestDtoList) {

		// 데이터베이스에서 취미를 랜덤으로 10개 선택
		List<Hobby> hobbies = Optional.ofNullable(hobbyRepository.findRandomHobbies())
			.filter(list -> !list.isEmpty())
			.orElseThrow(() -> new SurveyExceptionHandler(
				GET_HOBBY_NOT_FOUND));

		log.info("Get Random Hobbies Size (가져온 취미 수) : {}", hobbies.size());

		if (hobbies.size() < 10) {
			throw new SurveyExceptionHandler(GET_HOBBY_SIZE_LESS);
		}

		List<UserHobby> userHobbies = new ArrayList<>();

		// 각 취미에 대해 임의의 적합도를 부여하여 UserHobby 엔티티를 생성하고 저장
		Random random = new Random();

		for (Hobby hobby : hobbies) {
			int fitRate = random.nextInt(101);  // 0 ~ 100 사이의 임의의 정수
			UserHobby userHobby = new UserHobby(uuid, hobby, fitRate);
			userHobbies.add(userHobby);
		}

		// 리스트를 fitRate 가 높은 순서로 정렬 ( 내림차순 정렬 )
		userHobbies.sort((uh1, uh2) -> uh2.getFitRate().compareTo(uh1.getFitRate()));

		// 정렬된 리스트를 저장
		for (UserHobby userHobby : userHobbies) {
			userHobbyRepository.save(userHobby);
		}
	}

	/**
	 * 기존 회원의 취미 데이터를 삭제 후 새로운 회원 별 취미 데이터를 생성
	 * @param uuid
	 * @param hobbySurveyRequestDtoList
	 */
	private void updateUserHobbies(String uuid,
		List<HobbySurveyRequestDto> hobbySurveyRequestDtoList) {

		// 해당 회원의 취미 데이터를 모두 삭제
		userHobbyRepository.deleteByUuid(uuid);

		// createUserHobbies 메소드를 호출하여 새로운 취미 데이터를 생성
		createUserHobbies(uuid, hobbySurveyRequestDtoList);
	}
}
