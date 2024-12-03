package com.thales.spring_security_sample.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
// @Table(name = "customer") // Needed if the class name is different from the
// table name
@Getter
@Setter
public class Customer {

    // @Column(name = email) // Needed if the field name is different from the
    // column name
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Needed if the field
    // has to be generated
    private String email;
    private String password;
    private String role;

}
