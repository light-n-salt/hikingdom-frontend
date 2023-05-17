package org.lightnsalt.hikingdom.batch.processor;

import java.time.LocalDate;

import org.lightnsalt.hikingdom.domain.entity.club.Club;
import org.lightnsalt.hikingdom.domain.entity.club.record.ClubRanking;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ClubRankingProcessor implements ItemProcessor<Club, ClubRanking> {
	private static final LocalDate now = LocalDate.now();
	private Long ranking = 0L;
	private Long previousScore = null;
	private Long sameScoreCount = 0L;

	@Override
	public ClubRanking process(Club club) {
		if (!club.getScore().equals(previousScore)) {
			ranking += sameScoreCount + 1;
			sameScoreCount = 0L;
		} else {
			sameScoreCount++;
		}

		ClubRanking clubRanking = ClubRanking.builder()
			.ranking(ranking)
			.score(club.getScore())
			.setDate(now)
			.build();

		previousScore = club.getScore();
		return clubRanking;
	}
}
