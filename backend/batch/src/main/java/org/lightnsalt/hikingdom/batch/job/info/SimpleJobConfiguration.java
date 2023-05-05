package org.lightnsalt.hikingdom.batch.job.info;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SimpleJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job simpleJob() {
		// simpleJob란 이름으로 Batch job 생성
		return jobBuilderFactory.get("simpleJob")
			.start(simpleStep1())
			.build();
	}

	@Bean
	public Step simpleStep1() {
		// simpleStep1란 이름으로 Batch step 생성
		return stepBuilderFactory.get("simpleStep1")
			// step 안에서 수행될 기능
			.tasklet((contribution, chunkContext) -> {
				log.info(">>>>> This is Step1");
				return RepeatStatus.FINISHED;
			})
			.build();
	}
}
