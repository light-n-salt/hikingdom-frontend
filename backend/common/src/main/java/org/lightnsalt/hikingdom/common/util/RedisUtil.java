package org.lightnsalt.hikingdom.common.util;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisUtil {
	private final StringRedisTemplate stringRedisTemplate;

	public void setValueWithExpiration(String key, String value, long expiration) {
		Duration duration = Duration.ofSeconds(expiration);
		stringRedisTemplate.opsForValue().set(key, value, duration);
	}

	public String getValue(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	public void deleteValue(String key) {
		stringRedisTemplate.delete(key);
	}
}
