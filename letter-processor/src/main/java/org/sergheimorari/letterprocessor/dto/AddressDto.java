package org.sergheimorari.letterprocessor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AddressDto {
  @NotBlank(message = "Street is mandatory")
  String street;

  @NotBlank(message = "City is mandatory")
  String city;

  @NotBlank(message = "State is mandatory")
  @Size(min = 2, max = 2, message = "State must be a two-letter abbreviation")
  String state;

  @NotBlank(message = "Zipcode is mandatory")
  @Pattern(regexp = "^\\d{5}(-\\{4})?$", message = "Invalid zipcode")
  String zip;
}