package hobbiedo.crew.application;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.crew.domain.Chat;
import hobbiedo.crew.dto.response.ChatHistoryDTO;
import hobbiedo.crew.dto.response.ChatHistoryListDTO;
import hobbiedo.crew.dto.response.ChatImageDTO;
import hobbiedo.crew.dto.response.ChatImageListDTO;
import hobbiedo.crew.global.exception.GlobalException;
import hobbiedo.crew.global.status.ErrorStatus;
import hobbiedo.crew.infrastructure.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChatServiceImp implements ChatService {
	private final ChatRepository chatRepository;

	@Override
	public List<ChatHistoryListDTO> getChatHistorySince(Long crewId, Instant since) {
		Instant end = since.plus(8, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS);

		List<Chat> chatList = chatRepository.findByCrewIdAndCreatedAtBetween(crewId, since, end);
		Map<LocalDate, List<Chat>> groupedByDate = chatList.stream()
			.collect(Collectors.groupingBy(
				chat -> LocalDate.ofInstant(chat.getCreatedAt(), ZoneId.systemDefault())));

		return groupedByDate.entrySet().stream()
			.map(entry -> {
				List<ChatHistoryDTO> chatHistoryDTOList = entry.getValue().stream()
					.map(ChatHistoryDTO::toDto)
					.toList();
				return ChatHistoryListDTO.toDto(entry.getKey(), chatHistoryDTOList);
			})
			.toList();
	}

	@Override
	public List<ChatImageListDTO> getChatsWithImageUrl(Long crewId) {
		List<Chat> chatList = chatRepository.findByCrewIdAndImageUrlExists(crewId);
		if (chatList.isEmpty()) {
			throw new GlobalException(ErrorStatus.NO_EXIST_IMAGE_CHAT);
		}

		// 데이터를 날짜별로 그룹화
		Map<LocalDate, List<Chat>> groupedByDate = chatList.stream()
			.collect(Collectors.groupingBy(
				chat -> LocalDate.ofInstant(chat.getCreatedAt(), ZoneId.systemDefault())));

		return groupedByDate.entrySet().stream()
			.map(entry -> {
				List<ChatImageDTO> chatImageDTOList = entry.getValue().stream()
					.map(ChatImageDTO::toDto)
					.collect(Collectors.toList());
				return ChatImageListDTO.toDto(entry.getKey(), chatImageDTOList);
			})
			.toList();
	}

	@Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
	@Transactional
	@Override
	public void deleteOldChatsWithImageUrl() {
		// 6개월 전 시각 계산
		LocalDateTime expiryDateTime = LocalDateTime.now().minusMonths(3);

		// LocalDateTime을 Instant로 변환
		Instant expiryDate = expiryDateTime.atZone(ZoneId.systemDefault()).toInstant();
		chatRepository.deleteByImageUrlExistsAndCreatedAtBefore(expiryDate);
	}
}
