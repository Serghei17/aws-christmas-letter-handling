package org.sergheimorari.letterprocessor.util;

import lombok.experimental.UtilityClass;
import org.sergheimorari.letterprocessor.dto.AddressDto;
import org.sergheimorari.letterprocessor.dto.LetterDto;
import org.sergheimorari.letterprocessor.model.Address;
import org.sergheimorari.letterprocessor.model.Letter;

@UtilityClass
public class LetterConverter {

  public static Letter letterDtoToLetter(LetterDto letterDto) {
    return Letter.builder()
        .email(letterDto.getEmail())
        .name(letterDto.getName())
        .wishes(letterDto.getWishes())
        .address(addressDtoToAddress(letterDto.getAddressDto()))
        .build();
  }

  public static Address addressDtoToAddress(AddressDto addressDto) {
    return Address.builder()
        .street(addressDto.getStreet())
        .city(addressDto.getCity())
        .state(addressDto.getState())
        .zip(addressDto.getZip())
        .build();
  }
}
