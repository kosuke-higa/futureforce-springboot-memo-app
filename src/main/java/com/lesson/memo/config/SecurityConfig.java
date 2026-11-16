package com.lesson.memo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {
				http
				.authorizeHttpRequests(auth -> auth
					.requestMatchers(
						"/css/**",
						"/js/**",
						"/images/**",
						"/favicon.ico"
					).permitAll()
					.requestMatchers(
						"/admin/signup",
						"/admin/signin",
						"/admin/create"
					).permitAll()
					.anyRequest().authenticated()
				)
				
				.formLogin(form -> form
					.loginPage("/admin/signin")
					.loginProcessingUrl("/admin/signin")
					.usernameParameter("email")
					.defaultSuccessUrl("/memos", true)
					.permitAll()
				)
				.logout(logout -> logout
					.logoutUrl("/admin/logout")
	                .logoutSuccessUrl("/admin/signin?logout")
	                .invalidateHttpSession(true)
                	.deleteCookies("JSESSIONID")
				);
				return http.build();
	}

    @Bean
    	PasswordEncoder passwordEncoder() {
				return new BCryptPasswordEncoder();
			}
	
}