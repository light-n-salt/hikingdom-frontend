package org.lightnsalt.hikingdom.common.security;

import java.util.List;

import org.lightnsalt.hikingdom.common.security.jwt.JwtAccessDeniedHandler;
import org.lightnsalt.hikingdom.common.security.jwt.JwtAuthenticationEntryPoint;
import org.lightnsalt.hikingdom.common.security.jwt.JwtAuthenticationFilter;
import org.lightnsalt.hikingdom.common.security.jwt.JwtAuthenticationProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(jwtAuthenticationProvider);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(
			List.of("http://localhost:3000", "http://localhost:8080", "https://hikingdom.kr",
				"http://hikingdom.kr:3001", "http://hikingdom.kr:8081", "http://hikingdom.kr:8080",
				"https://k8a102.p.ssafy.io", "http://70.12.246.181:3000", "http://192.168.219.102:3000"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowCredentials(true);
		configuration.addAllowedHeader("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.authenticationProvider(jwtAuthenticationProvider)
			.cors()
			.and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.authorizeRequests()
			.antMatchers("/api/v1/members/auth/**").permitAll()
			.antMatchers("/v3/api-docs/**").permitAll()
			.antMatchers("/swagger-ui/**").permitAll()
			.antMatchers("/api/v1/hiking/**").permitAll()
			.antMatchers("/chat/ws/**").permitAll()
			.antMatchers("/api/v1/hiking/ws/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
			.accessDeniedHandler(jwtAccessDeniedHandler)
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
			.addFilterBefore(jwtAuthenticationFilter, LogoutFilter.class)
			.build();
	}
}
