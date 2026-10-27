package com.financiera.loansandbooks.controller.Dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    @NotBlank(message = "Title is mandatory.")
    @Size(min = 3, message = "Title must have at least 3 characters.")
    private String title;

    @NotNull(message = "Stock is mandatory.")
    @Min(value = 0, message = "Stock can not be negative.")
    private Integer stock;
}
