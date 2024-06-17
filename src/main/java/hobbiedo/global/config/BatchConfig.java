package hobbiedo.global.config;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import hobbiedo.batch.application.BoardStatsService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final BoardStatsService boardStatsService;

	// @Bean
	// public Job boardStatsUpdateJob() {
	// 	return new JobBuilder("boardStatsUpdateJob", jobRepository)
	// 		.start(boardStatsUpdateStep())
	// 		.build();
	// }
	//
	// @Bean
	// public Step boardStatsUpdateStep() {
	// 	return new StepBuilder("boardStatsUpdateStep", jobRepository)
	// 		.tasklet((contribution, chunkContext) -> {
	// 			updateBoardStats();
	// 			return RepeatStatus.FINISHED;
	// 		})
	// 		.transactionManager(transactionManager)
	// 		.build();
	// }
	//
	// private void updateBoardStats() {
	// 	boardStatsService.updateBoardCommentStats();
	// 	boardStatsService.updateBoardLikeStats();
	// }
	//
	// @Bean
	// public Tasklet boardStatsTasklet() {
	// 	return new BoardStatsTasklet();
	// }
	//
	// @Bean
	// @StepScope
	// public ItemReader<BoardStats> boardStatsReader() {
	// 	// BoardStatsReader 구현 클래스 인스턴스 반환
	// 	return new BoardStatsReader();
	// }
	//
	// @Bean
	// @StepScope
	// public ItemProcessor<BoardStats, BoardStats> boardStatsProcessor() {
	// 	// BoardStatsProcessor 구현 클래스 인스턴스 반환
	// 	return new BoardStatsProcessor();
	// }
	//
	// @Bean
	// @StepScope
	// public ItemWriter<BoardStats> boardStatsWriter() {
	// 	// BoardStatsWriter 구현 클래스 인스턴스 반환
	// 	return new BoardStatsWriter(boardStatsService);
	// }
}
