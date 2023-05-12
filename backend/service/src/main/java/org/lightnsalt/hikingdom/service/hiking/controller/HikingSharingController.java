package org.lightnsalt.hikingdom.service.hiking.controller;

import org.lightnsalt.hikingdom.service.hiking.dto.request.HikingLocationReq;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HikingSharingController {
	private final SimpMessagingTemplate template;

	@MessageMapping("/clubs/{clubId}/meetups/{meetupId}/sharing")
	public void shareLocation(@DestinationVariable Long clubId, @DestinationVariable Long meetupId,
		HikingLocationReq hikingLocationReq) {
		log.info("clubId {} ", clubId);
		log.info("meetupId {} ", meetupId);
		log.info("hikingLocationReq {} ", hikingLocationReq);
		template.convertAndSend("/sub/clubs/" + clubId + "/meetups/" + meetupId + "/sharing", hikingLocationReq);
	}
}
