package hobbiedo.batch.application;

import hobbiedo.batch.dto.response.BoardStatsResponseDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCommentDeleteDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCommentUpdateDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCreateEventDto;
import hobbiedo.batch.kafka.dto.consumer.BoardDeleteEventDto;
import hobbiedo.batch.kafka.dto.consumer.BoardLikeUpdateDto;

public interface BoardStatsService {

	void createBoardStats(BoardCreateEventDto eventDto);

	void deleteBoardStats(BoardDeleteEventDto eventDto);

	void updateBoardCommentStats(BoardCommentUpdateDto eventDto);

	void deleteBoardCommentStats(BoardCommentDeleteDto eventDto);

	void updateBoardLikeStats(BoardLikeUpdateDto eventDto);

	void deleteBoardLikeStats(BoardLikeUpdateDto eventDto);

	BoardStatsResponseDto getBoardStats(Long boardId);
}
