package com.tslcompany.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@ComponentScan
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.authorizeHttpRequests(request -> request
                        .requestMatchers(mvc.pattern("/")).permitAll()
                        .requestMatchers(mvc.pattern("/img/**")).permitAll()
                        .requestMatchers(mvc.pattern("/styles/**")).permitAll()
                        .requestMatchers(mvc.pattern("/registration")).permitAll()
                        .requestMatchers(mvc.pattern("/register")).permitAll()
                        .requestMatchers(mvc.pattern("/confirmation")).permitAll()
                        .requestMatchers(mvc.pattern("/forwarder")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/add-client")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/add-carrier")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/add-cargo")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/add-order")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/save-order")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/orders-list")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/update-order-status")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/order-status-confirmation")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/show-all-cargos")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/show-all-orders")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/forwarders")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/forwarder-panel")).hasAnyRole("FORWARDER", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/bookkeeping-panel")).hasAnyRole("ACCOUNTANT", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/bookkeeping")).hasAnyRole("ACCOUNTANT", "MANAGEMENT")
                        .requestMatchers(mvc.pattern("/management")).hasRole("MANAGEMENT")
                        .requestMatchers(mvc.pattern("/employee-management")).hasRole("MANAGEMENT")
                        .requestMatchers(mvc.pattern("/forwarder-list")).hasRole("MANAGEMENT")
                        .requestMatchers(mvc.pattern("/delete-forwarders")).hasRole("MANAGEMENT")
                        .requestMatchers(mvc.pattern("/forwarders")).hasRole("MANAGEMENT")
                        .requestMatchers(mvc.pattern("/assign-task-employees")).hasRole("MANAGEMENT")
//                .requestMatchers(mvc.pattern("/assign-task")).hasRole("MANAGEMENT")
                        .requestMatchers(mvc.pattern("/assign-role")).hasRole("MANAGEMENT")
                        .requestMatchers(mvc.pattern("/home-page")).authenticated()

                        .anyRequest().authenticated()
        );
        http.formLogin(login -> login.loginPage("/login").permitAll()
                .defaultSuccessUrl("/home-page", true));
        http.logout(logout -> logout.logoutRequestMatcher(mvc.pattern(HttpMethod.GET, "/logout/**"))
                .logoutSuccessUrl("/").permitAll());

        return http.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
