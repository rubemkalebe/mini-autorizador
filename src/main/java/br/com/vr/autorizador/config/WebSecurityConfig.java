package br.com.vr.autorizador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    
	@Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
    		.httpBasic()
    			.disable()
			.csrf()
				.disable()
	        .sessionManagement()
	        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()                
	        	.authorizeRequests()
	        		.antMatchers(WebURIs.PUBLIC_URI)
	        		.permitAll()
	        		
	        		.antMatchers(WebURIs.PROTECTED_URI)
	        		.permitAll()
	        		
	        		.anyRequest()
		            .denyAll()
	        .and()
	        	.exceptionHandling();
    	
    	return http.build();
    }
}
