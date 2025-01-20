package org.sergheimorari.letterprocessor.utils;

import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.params.provider.Arguments;
import org.sergheimorari.letterprocessor.model.Address;
import org.sergheimorari.letterprocessor.model.Letter;

@UtilityClass
public class LetterProcessorTestUtils {

  public static Letter createTestLetter(Address address) {
    var email = String.format("%s@test.com", RandomString.make());
    var name = "Sanda";
    var wishes = "I would like peace and bread for all!";

    return new Letter(email, name, wishes, address);
  }

  public static Stream<Arguments> provideInvalidLetters() {
    var senderLocation = createTestAddress();

    return Stream.of(
        Arguments.of(
            new Letter("xx", "Alex", "Some books", senderLocation), "Please provide a valid email"),
        Arguments.of(
            new Letter("test@example.com", "Jonas", null, senderLocation),
            "What is your best wish for this Christmas?"),
        Arguments.of(
            new Letter("test@example.com", "Lala", "More books", null),
            "Santa has to know where to send the present :)"),
        Arguments.of(
            createTestLetter(
                new Address(
                    null,
                    senderLocation.getCity(),
                    senderLocation.getState(),
                    senderLocation.getZip())),
            "Street is mandatory"),
        Arguments.of(
            createTestLetter(
                new Address(
                    senderLocation.getStreet(),
                    "",
                    senderLocation.getState(),
                    senderLocation.getZip())),
            "City is mandatory"),
        Arguments.of(
            createTestLetter(
                new Address(
                    senderLocation.getStreet(),
                    senderLocation.getCity(),
                    "Romania",
                    senderLocation.getZip())),
            "State must be a two-letter abbreviation"),
        Arguments.of(
            createTestLetter(
                new Address(
                    senderLocation.getStreet(),
                    senderLocation.getCity(),
                    senderLocation.getState(),
                    "123")),
            "Invalid zipcode"));
  }

  public static Address createTestAddress() {
    return new Address("Bratianu", "Sibiu", "SB", "500101");
  }
}
