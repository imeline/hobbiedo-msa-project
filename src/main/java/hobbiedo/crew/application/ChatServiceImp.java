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
		// since 날짜 기준 8일 후 정각을 계산
		// since 날짜 기준 8일 후 정각을 계산
		Instant end = since.plus(8, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS);
		log.info("Fetching chat history for crewId: {} from {} to {}", crewId, since, end);

		// crewId와 since ~ end 사이의 데이터를 조회
		List<Chat> chatList = chatRepository.findByCrewIdAndCreatedAtBetween(crewId, since, end);
		log.info("Number of chats found: {}", chatList.size());

		if (chatList.isEmpty()) {
			log.warn("No chats found for the given period.");
		} else {
			chatList.forEach(chat -> log.info("Chat found: {}", chat));
		}

		// 데이터를 날짜별로 그룹화
		Map<LocalDate, List<Chat>> groupedByDate = chatList.stream()
			.collect(Collectors.groupingBy(
				chat -> LocalDate.ofInstant(chat.getCreatedAt(), ZoneId.systemDefault())));

		groupedByDate.forEach((date, chats) -> log.info("Date: {}, Number of chats: {}", date, chats.size()));

		// 그룹화된 데이터를 DTO로 변환
		return groupedByDate.entrySet().stream()
			.map(entry -> {
				List<ChatHistoryDTO> chatHistoryDTOList = entry.getValue().stream()
					.map(ChatHistoryDTO::toDto)
					.collect(Collectors.toList());
				return ChatHistoryListDTO.toDto(entry.getKey(), chatHistoryDTOList);
			})
			.toList();
	}



	@Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
	@Transactional
	@Override
	public void deleteOldChatsWithImageUrl() {
		// 6개월 전 시각 계산
		LocalDateTime expiryDateTime = LocalDateTime.now().minusMonths(6);

		// LocalDateTime을 Instant로 변환
		Instant expiryDate = expiryDateTime.atZone(ZoneId.systemDefault()).toInstant();
		chatRepository.deleteByImageUrlExistsAndCreatedAtBefore(expiryDate);
	}
}
