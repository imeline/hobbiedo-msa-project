package hobbiedo.batch.application;

import hobbiedo.batch.kafka.dto.BoardCreateEventDto;

public interface BoardStatsService {

	void createBoardStats(BoardCreateEventDto eventDto);
}
