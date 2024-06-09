package hobbiedo.survey.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.global.api.exception.handler.SurveyExceptionHandler;
import hobbiedo.survey.domain.UserHobby;
import hobbiedo.survey.dto.response.UserHobbyResponseDto;
import hobbiedo.survey.infrastructure.UserHobbyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HobbyServiceImpl implements HobbyService {

	private final UserHobbyRepository userHobbyRepository;

	@Override
	public List<UserHobbyResponseDto> getUserHobbies(String uuid) {

		List<UserHobby> userHobbies = Optional.ofNullable(userHobbyRepository.findByUuid(uuid))
				.filter(list -> !list.isEmpty())
				.orElseThrow(() -> new SurveyExceptionHandler(GET_USER_HOBBIES_NOT_FOUND));

		// 회원 별 취미 데이터가 10개 미만일 경우 예외 처리
		if (userHobbies.size() < 10) {
			throw new SurveyExceptionHandler(GET_USER_HOBBIES_LESS);
		}

		List<UserHobbyResponseDto> getUserHobbyDtoList = userHobbies.stream()
				.map(UserHobbyResponseDto::userHobbyToDto)
				.toList();

		return getUserHobbyDtoList;
	}
}
