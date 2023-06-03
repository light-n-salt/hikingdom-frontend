package org.lightnsalt.hikingdom.config_server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final ConfigAuthenticationEntryPoint configAuthenticationEntryPoint;
	private final PasswordEncoder passwordEncoder;
	@Value("${spring.cloud.config.username}")
	private String username;
	@Value("${spring.cloud.config.password}")
	private String password;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf().disable()
			.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.httpBasic()
			.authenticationEntryPoint(configAuthenticationEntryPoint)
			.and().build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser(username)
			.password(passwordEncoder.encode(password))
			.roles("ADMIN");
	}
}
