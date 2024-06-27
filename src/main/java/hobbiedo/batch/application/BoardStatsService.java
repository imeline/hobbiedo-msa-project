package hobbiedo.batch.application;

import hobbiedo.batch.dto.response.BoardCommentDto;
import hobbiedo.batch.dto.response.BoardLikeDto;
import hobbiedo.batch.dto.response.BoardStatsResponseDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCreateEventDto;
import hobbiedo.batch.kafka.dto.consumer.BoardDeleteEventDto;

public interface BoardStatsService {

	// 게시글 통계 생성
	void createBoardStats(BoardCreateEventDto eventDto);

	// 게시글 통계 삭제
	void deleteBoardStats(BoardDeleteEventDto eventDto);

	// 게시글 통계 조회
	BoardStatsResponseDto getBoardStats(Long boardId);

	// 게시글 좋아요 수 갱신
	void updateBoardLikeStats(BoardLikeDto eventDto);

	// 게시글 댓글 수 갱신
	void updateBoardCommentStats(BoardCommentDto eventDto);
}
