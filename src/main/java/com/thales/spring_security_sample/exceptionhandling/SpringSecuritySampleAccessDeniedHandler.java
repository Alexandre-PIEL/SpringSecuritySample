package com.thales.spring_security_sample.exceptionhandling;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SpringSecuritySampleAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message = (accessDeniedException != null && accessDeniedException.getMessage() != null)
                ? accessDeniedException.getMessage()
                : "Authorization failed";
        String path = request.getRequestURI();
        response.setHeader("spring-boot-sample-error-reason", "Authorization failed");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        String jsonBody = String.format(
                "{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
                currentTimeStamp, HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(),
                message, path);
        response.getWriter().write(jsonBody);
    }

}
