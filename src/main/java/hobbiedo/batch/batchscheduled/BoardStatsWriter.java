package hobbiedo.batch.batchscheduled;

import hobbiedo.batch.application.BoardStatsService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardStatsWriter {

	private final BoardStatsService boardStatsService;

	// public void write(BoardStats item) {
	// 데이터 저장 로직 구현
	// 	boardStatsService.updateBoardStats(item);
	// }
}
