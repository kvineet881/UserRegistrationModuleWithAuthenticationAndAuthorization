package com.edusol.retailbanking.application.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class ErrorMessage {
    private Date timeStamp;
    private String message;
}
