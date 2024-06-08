package hobbiedo.crew.application;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.chat.domain.ChatStatus;
import hobbiedo.chat.mongoInfrastructure.ChatStatusRepository;
import hobbiedo.crew.domain.Chat;
import hobbiedo.crew.dto.response.ChatHistoryDTO;
import hobbiedo.crew.dto.response.ChatHistoryListDTO;
import hobbiedo.crew.dto.response.ChatImageDTO;
import hobbiedo.crew.dto.response.ChatImageListDTO;
import hobbiedo.crew.global.exception.GlobalException;
import hobbiedo.crew.global.status.ErrorStatus;
import hobbiedo.crew.mongoInfrastructure.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChatServiceImp implements ChatService {
	private final ChatRepository chatRepository;
	private final ChatStatusRepository chatStatusRepository;

	@Override
	public List<ChatHistoryListDTO> getChatHistoryBefore(Long crewId, String uuid, int page) {
		int size = 10; // 페이지 당 데이터 개수
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));

		ChatStatus chatUnReadStatus = chatStatusRepository.findByUuidAndCrewId(uuid, crewId)
			.orElseThrow(() -> new GlobalException(ErrorStatus.NO_EXIST_CHAT_UNREAD_STATUS));

		Instant lastReadAt = chatUnReadStatus.getLastReadAt();

		List<Chat> chatList = chatRepository.findByCrewIdAndCreatedAtAfter(crewId, lastReadAt,
			pageable);
		if (chatList.isEmpty()) {
			throw new GlobalException(ErrorStatus.NO_EXIST_CHAT);
		}
		Map<LocalDate, List<Chat>> groupedByDate = chatList.stream()
			.collect(Collectors.groupingBy(
				chat -> LocalDate.ofInstant(chat.getCreatedAt(), ZoneId.systemDefault())));

		return groupedByDate.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.map(entry -> {
				List<ChatHistoryDTO> chatHistoryDTOList = entry.getValue().stream()
					.sorted(Comparator.comparing(Chat::getCreatedAt))
					.map(ChatHistoryDTO::toDto)
					.toList();
				return ChatHistoryListDTO.toDto(entry.getKey(), chatHistoryDTOList);
			})
			.toList();
	}

	@Override
	public List<ChatImageListDTO> getChatsWithImageUrl(Long crewId) {
		List<Chat> chatList = chatRepository.findByCrewIdAndImageUrl(crewId);
		if (chatList.isEmpty()) {
			throw new GlobalException(ErrorStatus.NO_EXIST_IMAGE_CHAT);
		}

		// 데이터를 날짜별로 그룹화
		Map<LocalDate, List<Chat>> groupedByDate = chatList.stream()
			.collect(Collectors.groupingBy(
				chat -> LocalDate.ofInstant(chat.getCreatedAt(), ZoneId.systemDefault())));

		return groupedByDate.entrySet().stream()
			.sorted(Map.Entry.<LocalDate, List<Chat>>comparingByKey().reversed())
			.map(entry -> {
				List<ChatImageDTO> chatImageDTOList = entry.getValue().stream()
					.map(ChatImageDTO::toDto)
					.sorted(Comparator.comparing(ChatImageDTO::getCreatedAt).reversed())
					.toList();
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
