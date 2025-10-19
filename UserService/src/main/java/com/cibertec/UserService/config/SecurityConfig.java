package com.cibertec.UserService.config;

import com.cibertec.UserService.security.CustomOAuth2UserService;
import com.cibertec.UserService.security.JwtAuthenticationFilter;
import com.cibertec.UserService.security.OAuth2LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService,
                         OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler,
                         JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF for API endpoints
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/login**",
                    "/error**",
                    "/oauth2/**",
                    "/favicon.ico",
                    "/webjars/**",
                    "/h2-console/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> {
                oauth2.userInfoEndpoint(userInfo -> 
                    userInfo.userService(customOAuth2UserService)
                );
                oauth2.successHandler(oAuth2LoginSuccessHandler);
            })
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .headers(headers -> headers.frameOptions().disable()); // For H2 Console

        return http.build();
    }
}
