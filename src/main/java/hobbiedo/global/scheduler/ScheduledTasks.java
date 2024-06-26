package hobbiedo.global.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class ScheduledTasks {

	private final JobLauncher jobLauncher;
	private final Job boardStatsJob;

	@Scheduled(cron = "0 */1 * * * *") // 매 1분마다 실행
	public void runJob() throws
		JobExecutionAlreadyRunningException,
		JobRestartException,
		JobInstanceAlreadyCompleteException,
		JobParametersInvalidException {

		JobParameters jobParameters = new JobParametersBuilder()
			.addLong("timestamp", System.currentTimeMillis())
			.toJobParameters();

		jobLauncher.run(boardStatsJob, jobParameters);
	}
}
