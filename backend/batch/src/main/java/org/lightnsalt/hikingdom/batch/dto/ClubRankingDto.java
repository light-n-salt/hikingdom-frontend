package org.lightnsalt.hikingdom.batch.dto;

import org.lightnsalt.hikingdom.domain.entity.club.Club;
import org.lightnsalt.hikingdom.domain.entity.club.record.ClubTotalHikingStatistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubRankingDto {
	private Club club;
	private ClubTotalHikingStatistic  clubTotalHikingStatistic;
	private Long score;
}