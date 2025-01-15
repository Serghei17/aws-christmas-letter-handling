package org.sergheimorari.lettersender.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Letter(
    @NotNull(message = "Please provide an email") @Email(message = "Please provide a valid email")
        String email,
    @NotBlank(message = "Name should be provided") String name,
    @NotBlank(message = "What would you like for Christmas?") String wishes,
    @NotNull(message = "Santa has to know where to send the present :)") @Valid Address address) {}
