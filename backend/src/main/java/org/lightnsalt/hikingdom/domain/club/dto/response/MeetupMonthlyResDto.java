package org.lightnsalt.hikingdom.domain.club.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupMonthlyResDto {
	private List<Integer> startAt;

}
