package org.lightnsalt.hikingdom.service.hiking.controller;

import org.lightnsalt.hikingdom.service.hiking.dto.request.HikingShareLocationReq;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HikingShareController {

	private final RedisTemplate<String, Object> redisTemplate;

	@MessageMapping("/clubs/{clubId}/meetups/{meetupId}/sharing")
	public void shareLocation(@DestinationVariable Long clubId, @DestinationVariable Long meetupId,
		HikingShareLocationReq hikingLocationReq) {
		log.info("clubId {} ", clubId);
		log.info("meetupId {} ", meetupId);
		log.info("hikingLocationReq {} ", hikingLocationReq);

		redisTemplate.convertAndSend("/sub/clubs/" + clubId + "/meetups/" + meetupId + "/sharing", hikingLocationReq);
	}

}
