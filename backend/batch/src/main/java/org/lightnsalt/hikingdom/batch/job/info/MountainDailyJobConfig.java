package org.lightnsalt.hikingdom.batch.job.info;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.lightnsalt.hikingdom.domain.entity.info.MountainDailyInfo;
import org.lightnsalt.hikingdom.domain.entity.info.MountainInfo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
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
	private final EntityManagerFactory entityManagerFactory;

	@Bean
	public Job dailyMountainJob() {
		return jobBuilderFactory.get("dailyMountainJob")
			.incrementer(new RunIdIncrementer()) // 매일 실행시마다 Job Parameter를 증가시키는 JobIncrementer 사용
			.flow(dailyMountainStep())
			.end()
			.build();
	}

	@Bean
	@JobScope
	public Step dailyMountainStep() {
		return stepBuilderFactory.get("dailyMountainStep")
			.<MountainInfo, MountainDailyInfo>chunk(100)
			.reader(mountainIdReader())
			.processor(mountainDailyProcessor())
			.writer(mountainDailyWriter())
			.allowStartIfComplete(true)
			.build();
	}

	// 랜덤으로 뽑은 id에 해당하는 MountainInfo를 가져오는 함수
	@Bean
	@StepScope
	public JpaPagingItemReader<MountainInfo> mountainIdReader() {
		JpaPagingItemReader<MountainInfo> reader = new JpaPagingItemReader<>();
		reader.setEntityManagerFactory(entityManagerFactory);

		reader.setQueryString("SELECT m FROM MountainInfo m WHERE m.id = :mountainId");
		reader.setParameterValues(Collections.singletonMap("mountainId", getRandomMountainId()));
		reader.setPageSize(100);
		reader.setSaveState(false);
		return reader;
	}

	// MountainDaily 세팅 후 리턴하는 함수
	@Bean
	@StepScope
	public ItemProcessor<MountainInfo, MountainDailyInfo> mountainDailyProcessor() {
		return mountain -> MountainDailyInfo.builder()
			.mountain(mountain)
			.setDate(LocalDate.now())
			.build();
	}

	// MountainDaily 저장하는 함수
	@Bean
	@StepScope
	public ItemWriter<MountainDailyInfo> mountainDailyWriter() {
		JpaItemWriter<MountainDailyInfo> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}

	@Bean
	@StepScope
	public JobParameters dailyMountainJobParams() {
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addDate("run.date", new Date());
		return builder.toJobParameters();
	}

	public Long getRandomMountainId() {
		EntityManager em = entityManagerFactory.createEntityManager();
		List<Long> ids = em.createQuery("SELECT m.id FROM MountainInfo m", Long.class).getResultList();
		em.close();
		int randomId = new Random().nextInt(ids.size());
		log.info("random id is : {}", randomId);
		return ids.get(randomId);
	}
}
