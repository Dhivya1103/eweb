package com.eweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class SecurityConfig {

	  @Autowired
	    private JwtAuthenticationFilter jwtAuthenticationFilter;	
	  @Autowired
	  AuthEntryPointJwt authEntryPointJwt;
	  

	    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
	        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	    }
//	swagger api doc
//	    @Bean	 
//	    public SecurityFilterChain swaggerSecurityChain(HttpSecurity http) throws Exception {
//	    	  http
//	          .csrf(csrf -> csrf.disable())
//	          .authorizeHttpRequests(auth -> auth
//	              .requestMatchers(
//	                  "/swagger-ui/**",
//	                  "/v3/api-docs/**",
//	                  "/swagger-ui.html"
//	              ).permitAll()
//	              .anyRequest().authenticated()
//	          );
//
//	      return http.build();
//	    }
//
//	    //App endpoints chain
//	    @Bean	    
//	    public SecurityFilterChain appSecurityChain(HttpSecurity http) throws Exception {
//	        http
//	            .csrf(csrf -> csrf.disable()).exceptionHandling(ex ->
//                ex.authenticationEntryPoint(authEntryPointJwt)
//        )
//	            .authorizeHttpRequests(auth -> auth
//	                .requestMatchers("/api/auth/**").permitAll()
//	                .anyRequest().authenticated()
//	            )
//	            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//	        return http.build();
//	    }

	    
	    @Bean
	    public SecurityFilterChain appSecurityChain(HttpSecurity http) throws Exception {

	        http
	            .csrf(csrf -> csrf.disable())
	            .exceptionHandling(ex ->
	                ex.authenticationEntryPoint(authEntryPointJwt)
	            )
	            .authorizeHttpRequests(auth -> auth
	                // Swagger allow
	                .requestMatchers(
	                    "/swagger-ui/**",
	                    "/v3/api-docs/**",
	                    "/swagger-ui.html"
	                ).permitAll()
	                // Auth APIs allow
	                .requestMatchers("/api/auth/**").permitAll()
	                // Others secured
	                .anyRequest().authenticated()
	            )
	            .addFilterBefore(
	                jwtAuthenticationFilter,
	                UsernamePasswordAuthenticationFilter.class
	            );
	        return http.build();
	    }


	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

}
