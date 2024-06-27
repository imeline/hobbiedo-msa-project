package hobbiedo.global.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import hobbiedo.batch.application.BoardStatsService;
import hobbiedo.batch.dto.response.BoardCommentDto;
import hobbiedo.batch.dto.response.BoardLikeDto;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfig {

	private final BoardStatsService boardStatsService;
	private final RedisTemplate<String, Object> redisTemplate;
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	@Bean
	public Job boardStatsJob() {
		return new JobBuilder("boardStatsJob", jobRepository)
			.start(boardLikeUpdateStep())
			.next(boardCommentUpdateStep())
			.build();
	}

	@JobScope
	@Bean
	public Step boardLikeUpdateStep() {
		return new StepBuilder("boardLikeUpdateStep", jobRepository)
			.<BoardLikeDto, BoardLikeDto>chunk(1000, transactionManager)
			.reader(boardLikeReader())
			.writer(boardLikeWriter())
			.build();
	}

	@JobScope
	@Bean
	public Step boardCommentUpdateStep() {
		return new StepBuilder("boardCommentUpdateStep", jobRepository)
			.<BoardCommentDto, BoardCommentDto>chunk(1000, transactionManager)
			.reader(boardCommentReader())
			.writer(boardCommentWriter())
			.build();
	}

	@StepScope
	@Bean
	public ItemReader<BoardLikeDto> boardLikeReader() {

		return new ItemReader<BoardLikeDto>() {

			private List<BoardLikeDto> boardLikeDtos;

			@Override
			public BoardLikeDto read() {

				if (boardLikeDtos == null) {

					Set<String> likeCountKeys = redisTemplate.keys("board:*:like:count");

					boardLikeDtos = new ArrayList<>();

					for (String likeCountKey : likeCountKeys) {

						Long boardId = Long.parseLong(likeCountKey.split(":")[1]);
						String likeCountStr = (String)redisTemplate.opsForValue()
							.get(likeCountKey);
						Integer likeCount = Integer.parseInt(likeCountStr);

						boardLikeDtos.add(new BoardLikeDto(boardId, likeCount));
					}
				}
				if (boardLikeDtos.isEmpty()) {

					return null;
				} else {

					return boardLikeDtos.remove(0);
				}
			}
		};
	}

	@StepScope
	@Bean
	public ItemWriter<BoardLikeDto> boardLikeWriter() {

		return items -> {
			items.forEach(item -> {

				boardStatsService.updateBoardLikeStats(item);
				String redisKey = "board:" + item.getBoardId() + ":like:count";
				redisTemplate.delete(redisKey);
			});
		};
	}

	@StepScope
	@Bean
	public ItemReader<BoardCommentDto> boardCommentReader() {

		return new ItemReader<BoardCommentDto>() {

			private List<BoardCommentDto> boardCommentDtos;

			@Override
			public BoardCommentDto read() {

				if (boardCommentDtos == null) {

					Set<String> commentCountKeys = redisTemplate.keys("board:*:comment:count");

					boardCommentDtos = new ArrayList<>();

					for (String commentCountKey : commentCountKeys) {

						Long boardId = Long.parseLong(commentCountKey.split(":")[1]);
						String commentCountStr = (String)redisTemplate.opsForValue()
							.get(commentCountKey);
						Integer commentCount = Integer.parseInt(commentCountStr);

						boardCommentDtos.add(new BoardCommentDto(boardId, commentCount));
					}
				}
				if (boardCommentDtos.isEmpty()) {

					return null;
				} else {

					return boardCommentDtos.remove(0);
				}
			}
		};
	}

	@StepScope
	@Bean
	public ItemWriter<BoardCommentDto> boardCommentWriter() {

		return items -> {
			items.forEach(item -> {

				boardStatsService.updateBoardCommentStats(item);
				String redisKey = "board:" + item.getBoardId() + ":comment:count";
				redisTemplate.delete(redisKey);
			});
		};
	}
}
