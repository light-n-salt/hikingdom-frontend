package org.lightnsalt.hikingdom.service.hiking.service;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.service.hiking.dto.request.HikingShareLocationReq;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class HikingShareSubscriber implements MessageListener {
	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, Object> redisTemplate;
	private final SimpMessagingTemplate messageTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			String locationMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());

			HikingShareLocationReq hikingLocationReq = objectMapper.readValue(locationMessage,
				HikingShareLocationReq.class);
			log.info("HikingShareSubscriber:onMessage locationReq {}", hikingLocationReq);

			messageTemplate.convertAndSend(
				"/sub/clubs/" + hikingLocationReq.getClubId() + "/meetups/" + hikingLocationReq.getMeetupId()
					+ "/sharing", hikingLocationReq);

		} catch (Exception e) {
			throw new GlobalException(ErrorCode.HIKING_LOCATION_SHARE_INVALID);
		}
	}
}
