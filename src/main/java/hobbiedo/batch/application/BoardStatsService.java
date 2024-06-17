package hobbiedo.batch.application;

import hobbiedo.batch.dto.response.BoardStatsResponseDto;
import hobbiedo.batch.kafka.dto.BoardCommentUpdateDto;
import hobbiedo.batch.kafka.dto.BoardCreateEventDto;
import hobbiedo.batch.kafka.dto.BoardDeleteEventDto;
import hobbiedo.batch.kafka.dto.BoardLikeUpdateDto;

public interface BoardStatsService {

	void createBoardStats(BoardCreateEventDto eventDto);

	void deleteBoardStats(BoardDeleteEventDto eventDto);

	void updateBoardCommentStats(BoardCommentUpdateDto eventDto);

	void updateBoardLikeStats(BoardLikeUpdateDto eventDto);

	BoardStatsResponseDto getBoardStats(Long boardId);
}
