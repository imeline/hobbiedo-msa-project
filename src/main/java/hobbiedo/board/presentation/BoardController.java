package hobbiedo.board.presentation;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.board.application.BoardService;
import hobbiedo.board.dto.request.BoardUploadRequestDto;
import hobbiedo.board.vo.request.BoardUploadRequestVo;
import hobbiedo.global.api.ApiResponse;
import hobbiedo.global.api.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/crews/{crew-id}/board")
@Tag(name = "Board", description = "소모임 게시판 서비스")
public class BoardController {

	private final BoardService boardService;

	@PostMapping("/create-post")
	@Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
	public ApiResponse<Void> createPost(
			@PathVariable("crew-id") Long crewId,
			@RequestHeader(name = "Uuid") String uuid,
			@RequestBody BoardUploadRequestVo boardUploadRequestVo) {

		BoardUploadRequestDto boardUploadRequestDto = BoardUploadRequestDto
				.boardUploadVoToDto(boardUploadRequestVo);

		boardService.createPostWithImages(crewId, uuid, boardUploadRequestDto);

		return ApiResponse.onSuccess(
				SuccessStatus.CREATE_POST_SUCCESS
		);
	}
}
