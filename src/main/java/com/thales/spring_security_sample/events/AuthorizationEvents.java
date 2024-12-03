package com.thales.spring_security_sample.events;

import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthorizationEvents {
    
    @EventListener
    public void onFailure(AuthorizationDeniedEvent authorizationDeniedEvent) {
        log.info("Authorization failed for the user : {} due to {}", authorizationDeniedEvent.getAuthentication().get().getName(), authorizationDeniedEvent.getAuthorizationDecision().toString());
    }
    
}
