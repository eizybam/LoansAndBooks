package com.financiera.loansandbooks.controller.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private List<String> messages;
}
