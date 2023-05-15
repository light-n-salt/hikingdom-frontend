package org.lightnsalt.hikingdom.batch.job.info;

import javax.persistence.EntityManagerFactory;

import org.lightnsalt.hikingdom.batch.dto.ClubRankingDto;
import org.lightnsalt.hikingdom.batch.processor.ClubRankingProcessor;
import org.lightnsalt.hikingdom.domain.entity.club.record.ClubRanking;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
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
public class ClubRankingJobConfig {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory;

	private final ClubRankingProcessor clubRankingProcessor;

	/**
	 * 모든 소모임의 총 통계를 가져옴
	 * @return JpaPagingItemReader
	 */
	@Bean
	@StepScope
	public JpaPagingItemReader<ClubRankingDto> clubTotalHikingStatisticReader() {
		JpaPagingItemReader<ClubRankingDto> reader = new JpaPagingItemReader<>();
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setQueryString("SELECT new org.lightnsalt.hikingdom.batch.dto.ClubRankingDto("
			+ "c AS club, ct AS clubTotalHikingStatistic, "
			+ "(ct.totalAlt * ct.totalMountainCount) AS score) "
			+ "FROM ClubTotalHikingStatistic ct "
			+ "JOIN Club c ON ct.club.id = c.id "
			+ "WHERE c.isDeleted = false "
			+ "ORDER BY score DESC");
		reader.setPageSize(Integer.MAX_VALUE); // read all items at once
		reader.setSaveState(false);
		return reader;
	}

	@Bean
	@StepScope
	public ItemWriter<ClubRanking> clubRankingWriter() {
		JpaItemWriter<ClubRanking> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}

	@Bean
	@JobScope
	public Step clubRankingStep() {
		return stepBuilderFactory.get("clubRankingStep")
			.<ClubRankingDto, ClubRanking>chunk(Integer.MAX_VALUE)
			.reader(clubTotalHikingStatisticReader())
			.processor(clubRankingProcessor)
			.writer(clubRankingWriter())
			.allowStartIfComplete(true)
			.build();
	}

	@Bean
	public Job clubRankingJob() {
		return jobBuilderFactory.get("clubRankingJob")
			.start(clubRankingStep())
			.build();
	}
}
