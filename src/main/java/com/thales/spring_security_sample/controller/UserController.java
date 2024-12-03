package com.thales.spring_security_sample.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thales.spring_security_sample.model.Customer;
import com.thales.spring_security_sample.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            String hashedPassword = passwordEncoder.encode(customer.getPassword());
            customer.setPassword(hashedPassword);
            customerRepository.save(customer);
            Optional<Customer> retrievedCustomer = customerRepository.findByEmail(customer.getEmail());
            if (retrievedCustomer.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body("The user registration succeded");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user registration failed");
            }
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured : " + exception.getMessage());
        }
    }

}
