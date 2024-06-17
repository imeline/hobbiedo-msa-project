package hobbiedo.batch.batchscheduled;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardStatsTasklet /*implements Tasklet*/ {

	private final BoardStatsReader reader;
	private final BoardStatsProcessor processor;
	private final BoardStatsWriter writer;

	// @Override
	// public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws
	// 	Exception {
	// 	BoardStats item;
	// 	while ((item = reader.read()) != null) {
	// 		BoardStats processedItem = processor.process(item);
	// 		writer.write(processedItem);
	// 	}
	// 	return RepeatStatus.FINISHED;
	// }
}
