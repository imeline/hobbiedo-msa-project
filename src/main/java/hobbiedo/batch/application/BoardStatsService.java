package hobbiedo.batch.application;

import hobbiedo.batch.kafka.dto.BoardCreateEventDto;
import hobbiedo.batch.kafka.dto.BoardDeleteEventDto;

public interface BoardStatsService {

	void createBoardStats(BoardCreateEventDto eventDto);

	void deleteBoardStats(BoardDeleteEventDto eventDto);
}
