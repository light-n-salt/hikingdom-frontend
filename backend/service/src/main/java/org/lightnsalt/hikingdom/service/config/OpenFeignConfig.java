package org.lightnsalt.hikingdom.service.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

@Configuration
@EnableFeignClients("org.lightnsalt.hikingdom.service.member.client")
public class OpenFeignConfig {
	/**
	 * OpenFeign Client로 요청을 보낼 때, JWT token를 header에 추가하는 RequestInterceptor를 생성하는 Bean
	 */
	@Bean
	public JwtAuthTokenInterceptor jwtTokenInterceptor() {
		return new JwtAuthTokenInterceptor();
	}

	/**
	 * 날짜 및 시간 포맷 설정을 위한 FeignFormatterRegistrar를 생성하는 Bean
	 * OpenFeign 사용 시, LocalDate/LocalDateTime/LocalTime를 처리하기 위해 다음과 같이 등록해야 한다.
	 */
	@Bean
	public FeignFormatterRegistrar dateTimeFormatterRegistrar() {
		return registry -> {
			DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
			registrar.setUseIsoFormat(true);
			registrar.registerFormatters(registry);
		};
	}
}