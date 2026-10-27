package com.financiera.loansandbooks.controller.Dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    @NotNull(message = "Reader ID is mandatory.")
    @Positive(message = "Reader ID must be greater than 0.")
    private Long readerId;

    @NotNull(message = "Book ID is mandatory.")
    @Positive(message = "Book ID must be greater than 0.")
    private Long bookId;
}
