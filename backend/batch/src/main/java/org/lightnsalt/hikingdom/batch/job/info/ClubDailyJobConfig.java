package org.lightnsalt.hikingdom.batch.job.info;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.lightnsalt.hikingdom.domain.entity.club.Club;
import org.lightnsalt.hikingdom.domain.entity.info.ClubDailyInfo;
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
public class ClubDailyJobConfig {
	/*
	오늘의 소모임 랜덤으로 하나 뽑기
		- 소모임 테이블 count 가져오기
		- 랜덤으로 하나 뽑기
	오늘의 소모임 정보 저장하기
		- 오늘의 소모임 정보 테이블에 저장하기
 */
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory;

	@Bean
	public Job dailyClubJob() {
		return jobBuilderFactory.get("dailyClubJob")
			.incrementer(new RunIdIncrementer()) // 매일 실행시마다 Job Parameter를 증가시키는 JobIncrementer 사용
			.flow(dailyClubStep())
			.end()
			.build();
	}

	@Bean
	@JobScope
	public Step dailyClubStep() {
		return stepBuilderFactory.get("dailyClubStep")
			.<Club, ClubDailyInfo>chunk(100)
			.reader(clubIdReader())
			.processor(clubDailyProcessor())
			.writer(clubDailyWriter())
			.allowStartIfComplete(true)
			.build();
	}

	// 랜덤으로 뽑은 id에 해당하는 MountainInfo를 가져오는 함수
	@Bean
	@StepScope
	public JpaPagingItemReader<Club> clubIdReader() {
		JpaPagingItemReader<Club> reader = new JpaPagingItemReader<>();
		reader.setEntityManagerFactory(entityManagerFactory);

		reader.setQueryString("SELECT c FROM Club c WHERE c.id = :clubId");
		reader.setParameterValues(Collections.singletonMap("clubId", getRandomClubId()));
		reader.setPageSize(100);
		reader.setSaveState(false);
		return reader;
	}

	// MountainDaily 세팅 후 리턴하는 함수
	@Bean
	@StepScope
	public ItemProcessor<Club, ClubDailyInfo> clubDailyProcessor() {
		return club -> ClubDailyInfo.builder()
			.club(club)
			.setDate(LocalDate.now())
			.build();
	}

	// MountainDaily 저장하는 함수
	@Bean
	@StepScope
	public ItemWriter<ClubDailyInfo> clubDailyWriter() {
		JpaItemWriter<ClubDailyInfo> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}

	@Bean
	@StepScope
	public JobParameters dailyClubJobParams() {
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addDate("run.date", new Date());
		return builder.toJobParameters();
	}

	public Long getRandomClubId() {
		EntityManager em = entityManagerFactory.createEntityManager();
		List<Long> ids = em.createQuery("SELECT c.id FROM Club c", Long.class).getResultList();
		em.close();
		int randomId = new Random().nextInt(ids.size());
		log.info("random id is : {}", randomId);
		return ids.get(randomId);
	}
}
