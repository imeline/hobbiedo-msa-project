package hobbiedo.batch.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.batch.application.BoardStatsService;
import hobbiedo.batch.dto.response.BoardStatsResponseDto;
import hobbiedo.batch.vo.response.BoardStatsResponseVo;
import hobbiedo.global.api.ApiResponse;
import hobbiedo.global.api.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/crew/board-stats")
@Tag(name = "BoardStats", description = "게시글 통계 서비스")
public class BoardStatsController {

	private final BoardStatsService boardStatsService;

	// 게시글 통계 조회
	@GetMapping("/{boardId}/stats")
	@Operation(summary = "게시글 통계 조회", description = "게시글의 댓글 수와 좋아요 수를 조회합니다.")
	public ApiResponse<BoardStatsResponseVo> getBoardStats(
		@PathVariable("boardId") Long boardId) {

		BoardStatsResponseDto boardStatsResponseDto = boardStatsService.getBoardStats(boardId);

		return ApiResponse.onSuccess(
			SuccessStatus.GET_BOARD_STATS_SUCCESS,
			BoardStatsResponseVo.boardStatsDtoToVo(boardStatsResponseDto)
		);
	}
}
