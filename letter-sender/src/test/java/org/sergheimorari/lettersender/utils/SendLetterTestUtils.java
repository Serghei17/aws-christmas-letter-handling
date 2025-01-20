package org.sergheimorari.lettersender.utils;

import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.params.provider.Arguments;
import org.sergheimorari.lettersender.model.Address;
import org.sergheimorari.lettersender.model.Letter;

@UtilityClass
public class SendLetterTestUtils {

  public static Letter createTestLetter(Address address) {
    var email = String.format("%s@test.com", RandomString.make());
    var name = "Sanda";
    var wishes = "I would like peace and bread for all!";

    return new Letter(email, name, wishes, address);
  }

  public static Stream<Arguments> provideInvalidLetters() {
    Address senderLocation = createTestAddress();

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
                    null, senderLocation.city(), senderLocation.state(), senderLocation.zip())),
            "Street is mandatory"),
        Arguments.of(
            createTestLetter(
                new Address(
                    senderLocation.street(), "", senderLocation.state(), senderLocation.zip())),
            "City is mandatory"),
        Arguments.of(
            createTestLetter(
                new Address(
                    senderLocation.street(),
                    senderLocation.city(),
                    "Romania",
                    senderLocation.zip())),
            "State must be a two-letter abbreviation"),
        Arguments.of(
            createTestLetter(
                new Address(
                    senderLocation.street(), senderLocation.city(), senderLocation.state(), "123")),
            "Invalid zipcode"));
  }

  public static Address createTestAddress() {
    return new Address("Bratianu", "Sibiu", "SB", "500101");
  }
}
