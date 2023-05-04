package org.lightnsalt.hikingdom.batch.job;

import javax.persistence.EntityManagerFactory;

import org.lightnsalt.hikingdom.domain.repository.info.MountainInfoRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class MountainDailyJobConfig {
	/*
		오늘의 산 랜덤으로 하나 뽑기
		- 오늘의 산 테이블 count 가져오기
		- 랜덤으로 하나 뽑기
		오늘의 산 정보 저장하기
		- 오늘의 산 정보 테이블에 저장하기
	 */
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final MountainInfoRepository mountainInfoRepository;
	private final EntityManagerFactory entityManagerFactory;

	@Bean
	public Job dailyMountainJob() throws Exception {
		return jobBuilderFactory.get("dailyMountainJob")
			.start(getRandomMountainStep())
			.next(saveMountainDailyStep())
			.build();
	}

	@Bean
	@JobScope
	public Step getRandomMountainStep() throws Exception {
		return stepBuilderFactory.get("getRandomMountainStep")
			.tasklet(randomMountainIdTasklet())
			.build();
	}

	@Bean
	@StepScope
	public Tasklet randomMountainIdTasklet() {
		return new RandomMountainIdTasklet(mountainInfoRepository); // MountainInfo에서 랜덤으로 하나 가져오는 task 수행
	}

	@Bean
	@JobScope
	public Step saveMountainDailyStep() throws Exception {
		return stepBuilderFactory.get("saveMountainDailyStep")
			.tasklet(randomMountainIdTasklet())
			.build();
	}

}
