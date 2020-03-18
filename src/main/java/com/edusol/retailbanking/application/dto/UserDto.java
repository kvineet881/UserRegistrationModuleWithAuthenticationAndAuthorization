package com.edusol.retailbanking.application.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

//private static final long serialVersionUID = 3206112545905525900L;
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationTocken;
    private boolean emailVerificationStatus =false;
}
