package org.lightnsalt.hikingdom.batch.processor;

import java.time.LocalDate;

import org.lightnsalt.hikingdom.batch.dto.ClubRankingDto;
import org.lightnsalt.hikingdom.domain.entity.club.record.ClubRanking;
import org.lightnsalt.hikingdom.domain.entity.club.record.ClubTotalHikingStatistic;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ClubRankingProcessor implements ItemProcessor<ClubRankingDto, ClubRanking> {
	private Long ranking = 1L;
	private ClubRanking previous = null;

	@Override
	public ClubRanking process(ClubRankingDto clubRankingDto) {
		ClubTotalHikingStatistic clubTotalHikingStatistic = clubRankingDto.getClubTotalHikingStatistic();
		if (previous != null && !clubRankingDto.getScore().equals(previous.getScore())) {
			ranking++;
		}

		ClubRanking clubRanking = ClubRanking.builder()
			.club(clubRankingDto.getClub())
			.ranking(ranking)
			.score(clubRankingDto.getScore())
			.setDate(LocalDate.now())
			.totalMeetupCount(clubTotalHikingStatistic.getTotalHikingCount())
			.totalMountainCount(clubTotalHikingStatistic.getTotalMountainCount())
			.build();

		previous = clubRanking;
		return clubRanking;
	}
}
