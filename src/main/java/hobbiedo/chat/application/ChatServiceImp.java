package hobbiedo.chat.application;

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

import hobbiedo.chat.domain.Chat;
import hobbiedo.chat.dto.response.ChatHistoryDTO;
import hobbiedo.chat.dto.response.ChatHistoryListDTO;
import hobbiedo.chat.dto.response.ChatImageDTO;
import hobbiedo.chat.dto.response.ChatImageListDTO;
import hobbiedo.chat.mongoInfrastructure.ChatRepository;
import hobbiedo.global.exception.GlobalException;
import hobbiedo.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChatServiceImp implements ChatService {
	private final ChatRepository chatRepository;

	@Override
	public List<ChatHistoryListDTO> getChatHistoryBefore(Long crewId, int page) {
		int size = 10; // 페이지 당 데이터 개수
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("createdAt")));
		List<Chat> chatList = chatRepository.findByCrewId(crewId, pageable);
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
