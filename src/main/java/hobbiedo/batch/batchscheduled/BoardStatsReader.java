package hobbiedo.batch.batchscheduled;

import java.util.List;

import hobbiedo.batch.domain.BoardStats;

public class BoardStatsReader {

	private final List<BoardStats> data;
	private int nextIndex;

	public BoardStatsReader(List<BoardStats> data) {
		this.data = data;
		this.nextIndex = 0;
	}

	public BoardStats read() {
		if (nextIndex < data.size()) {
			return data.get(nextIndex++);
		}
		return null;
	}
}
