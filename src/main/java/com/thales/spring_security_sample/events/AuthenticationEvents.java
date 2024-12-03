package com.thales.spring_security_sample.events;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationEvents {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent authenticationSuccessEvent) {
        log.info("Login successful for the user : {}", authenticationSuccessEvent.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent abstractAuthenticationFailureEvent) {
        log.info("Login failed for the user : {}", abstractAuthenticationFailureEvent.getException().getMessage());
    }
    
}
