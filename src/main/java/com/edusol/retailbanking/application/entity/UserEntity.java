package com.edusol.retailbanking.application.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data

@Entity(name = "users")
public class UserEntity implements Serializable {

   // private static final long serialVersionUID = -9060447717593224201L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    private String encryptedPassword;
    private String emailVerificationTocken;

    @Column
    private boolean emailVerificationStatus =false ;
}
