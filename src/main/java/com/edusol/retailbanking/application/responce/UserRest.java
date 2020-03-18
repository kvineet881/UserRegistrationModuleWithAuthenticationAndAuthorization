package com.edusol.retailbanking.application.responce;

import lombok.Data;

@Data
public class UserRest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
