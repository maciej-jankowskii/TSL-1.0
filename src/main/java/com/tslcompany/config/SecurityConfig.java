package com.tslcompany.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.authorizeHttpRequests(request -> request
                .requestMatchers(mvc.pattern("/")).permitAll()
                .requestMatchers(mvc.pattern("/img/**")).permitAll()
                .requestMatchers(mvc.pattern("/styles/**")).permitAll()
                .anyRequest().authenticated()
        );
        http.formLogin(login -> login.loginPage("/login").permitAll()
                .defaultSuccessUrl("/home-page", true));
        http.logout(logout -> logout.logoutRequestMatcher(mvc.pattern(HttpMethod.GET, "/logout/**"))
                .logoutSuccessUrl("/").permitAll());
        http.csrf().disable();

        return http.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }


}
