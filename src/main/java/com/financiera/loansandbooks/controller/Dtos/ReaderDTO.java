package com.financiera.loansandbooks.controller.Dtos;

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
public class ReaderDTO {
    @NotBlank(message = "Name is mandatory.")
    @Size(min = 3, message = "Name must have at least 3 characters.")
    private String name;

    @NotNull(message = "You must indicate whether the user is active.")
    private Boolean active;

    @NotNull(message = "You must indicate whether the user is premium.")
    private Boolean premium;
}
