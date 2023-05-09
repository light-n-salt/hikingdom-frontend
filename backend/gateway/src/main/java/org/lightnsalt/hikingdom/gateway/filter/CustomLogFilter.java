package org.lightnsalt.hikingdom.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomLogFilter implements GatewayFilterFactory<CustomLogFilter.Config> {
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			log.info("CustomLogFilter pre-filter: Request Path -> {}", request.getPath());

			return chain.filter(exchange).then(Mono.fromRunnable(
				() -> log.info("CustomLogFilter post-filter: Response Status -> {}", response.getStatusCode())));
		};
	}

	@Override
	public Class<CustomLogFilter.Config> getConfigClass() {
		return CustomLogFilter.Config.class;
	}

	@Data
	public static class Config {
	}
}
