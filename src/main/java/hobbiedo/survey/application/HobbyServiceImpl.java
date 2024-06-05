package hobbiedo.survey.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.global.api.exception.handler.SurveyExceptionHandler;
import hobbiedo.survey.domain.UserHobby;
import hobbiedo.survey.dto.response.GetUserHobbyDto;
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
	public List<GetUserHobbyDto> getUserHobbies(String uuid) {

		List<UserHobby> userHobbies = Optional.ofNullable(userHobbyRepository.findByUuid(uuid))
			.filter(list -> !list.isEmpty())
			.orElseThrow(() -> new SurveyExceptionHandler(GET_USER_HOBBIES_NOT_FOUND));

		List<GetUserHobbyDto> getUserHobbyDtoList = userHobbies.stream()
			.map(GetUserHobbyDto::userHobbyToDto)
			.toList();

		return getUserHobbyDtoList;
	}
}
