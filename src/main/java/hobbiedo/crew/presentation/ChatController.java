package hobbiedo.crew.presentation;

import java.time.Instant;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.crew.application.ChatService;
import hobbiedo.crew.dto.response.ChatHistoryListDTO;
import hobbiedo.crew.dto.response.ChatImageListDTO;
import hobbiedo.crew.global.base.BaseResponse;
import hobbiedo.crew.global.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users/chat")
@RequiredArgsConstructor
@Tag(name = "Chat-mvc", description = "채팅(동기) API 입니다")
public class ChatController {
	private final ChatService chatService;

	@Operation(summary = "(특정 소모임의) 이전 채팅 내역 조회", description = "특정 시간 다음날 0시부터 7일간의 채팅 내역을 조회한다.")
	@GetMapping("/history/{crewId}")
	public BaseResponse<List<ChatHistoryListDTO>> getChatHistorySince(@PathVariable Long crewId,
		@RequestParam Instant since) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_CHAT_HISTORY,
			chatService.getChatHistorySince(crewId, since));
	}

	@Operation(summary = "(특정 소모임) 사진 모아보기", description = "특정 소모임의 이미지 URL이 있는 채팅 내역을 조회한다.")
	@GetMapping("/image/{crewId}")
	public BaseResponse<List<ChatImageListDTO>> getChatsWithImageUrl(@PathVariable Long crewId) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_IMAGE_CHAT,
			chatService.getChatsWithImageUrl(crewId));
	}

}
