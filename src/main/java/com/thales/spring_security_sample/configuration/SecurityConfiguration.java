package com.thales.spring_security_sample.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.thales.spring_security_sample.exceptionhandling.SpringSecuritySampleAccessDeniedHandler;
import com.thales.spring_security_sample.exceptionhandling.SpringSecuritySampleBasicAuthenticationEntryPoint;
import com.thales.spring_security_sample.filter.CsrfCookieFilter;

import jakarta.servlet.http.HttpServletRequest;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collections;

@Configuration
@Profile("!prod")
public class SecurityConfiguration {

    // To initialize the default security filters
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        
        httpSecurity
            .cors(customizer -> customizer.configurationSource(new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
                    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfiguration.setMaxAge(3600L);
                    return corsConfiguration;
                }
            }))
            .sessionManagement(customizer -> customizer.invalidSessionUrl("/invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true).expiredUrl("/expiredSession"))
            .requiresChannel(customizer -> customizer.anyRequest().requiresInsecure())
            .csrf(customizer -> customizer.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler).csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/customer").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/customers").authenticated()
                .anyRequest().permitAll());

        httpSecurity.formLogin(withDefaults());
        httpSecurity.httpBasic(customizer -> customizer.authenticationEntryPoint(new SpringSecuritySampleBasicAuthenticationEntryPoint()));
        httpSecurity.exceptionHandling(ehc -> ehc.accessDeniedHandler(new SpringSecuritySampleAccessDeniedHandler()));
        return httpSecurity.build();
    }

    // To encode passwords by hashing (the default function is BCrypt)
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // To check that the passwords used have not been exposed in data breaches (cf.
    // https://haveibeenpwned.com/API/v3#PwnedPasswords)
    @Bean
    CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
