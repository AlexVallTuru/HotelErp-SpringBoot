package com.erphotel.AuthSecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class ConfigurationAutentificator {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void autenticacio(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    String[] staticResources = {
        "/css/**",
        "/images/**",
        "/fonts/**",
        "/scripts/**",
        "/error/**",
        "/static/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .headers().frameOptions().disable().and()
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers(staticResources).permitAll()
                .requestMatchers("/homeScript.js","/rooms/cleaning/**", "/gestionHabitaciones/**", "/assets/**", "/homeStyle.css", "/error/**", "/functions/**", "/", "home", "/home/**").hasAnyAuthority("limpieza", "staff", "recepcion")
                .requestMatchers("/", "home", "/home/**", "/rooms/**","/person/**", "/savePersona", "/homeScript.js", "/invoiceManager/**", "/invoice/**", "/invoiceLines/**", "/assets/**", "/homeStyle.css", "/error/**", "/functions/**", "/hotel_booking/**").hasAnyAuthority("recepcion", "staff")
                .requestMatchers("/**").hasAnyAuthority("staff")
                .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
                )
                .logout((logout) -> logout.
                permitAll()
                )
                .exceptionHandling((exception) -> exception
                .accessDeniedPage("/error/error403")
                )
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
