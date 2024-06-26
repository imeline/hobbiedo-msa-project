package hobbiedo.batch.application;

import hobbiedo.batch.dto.response.BoardCommentDto;
import hobbiedo.batch.dto.response.BoardLikeDto;
import hobbiedo.batch.dto.response.BoardStatsResponseDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCommentDeleteDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCommentUpdateDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCreateEventDto;
import hobbiedo.batch.kafka.dto.consumer.BoardDeleteEventDto;
import hobbiedo.batch.kafka.dto.consumer.BoardLikeUpdateDto;

public interface BoardStatsService {

	// 게시글 통계 생성
	void createBoardStats(BoardCreateEventDto eventDto);

	// 게시글 통계 삭제
	void deleteBoardStats(BoardDeleteEventDto eventDto);

	// 곧 삭제될 댓글 수 증가 메서드
	void increaseBoardCommentStats(BoardCommentUpdateDto eventDto);

	// 곧 삭제될 댓글 수 감소 메서드
	void decreaseBoardCommentStats(BoardCommentDeleteDto eventDto);

	// 곧 삭제될 좋아요 수 증가 메서드
	void increaseBoardLikeStats(BoardLikeUpdateDto eventDto);

	// 곧 삭제될 좋아요 수 감소 메서드
	void decreaseBoardLikeStats(BoardLikeUpdateDto eventDto);

	// 게시글 통계 조회
	BoardStatsResponseDto getBoardStats(Long boardId);

	// 게시글 좋아요 수 갱신
	void updateBoardLikeStats(BoardLikeDto eventDto);

	// 게시글 댓글 수 갱신
	void updateBoardCommentStats(BoardCommentDto eventDto);
}
