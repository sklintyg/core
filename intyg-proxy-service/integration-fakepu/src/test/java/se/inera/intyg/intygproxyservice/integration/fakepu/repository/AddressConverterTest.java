package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.CARE_OF;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.POSTAL_ADDRESS1;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.POSTAL_ADDRESS2;
import static se.inera.intyg.intygproxyservice.integration.fakepu.repository.AddressConverter.convert;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedAddress;

class AddressConverterTest {

  private static Stream<Arguments> provideAddressCombinations() {
    return Stream.of(
        Arguments.of(
            ParsedAddress.builder()
                .careOf(CARE_OF)
                .build(),
            CARE_OF
        ),
        Arguments.of(
            ParsedAddress.builder()
                .postalAddress1(POSTAL_ADDRESS1)
                .build(),
            POSTAL_ADDRESS1
        ),
        Arguments.of(
            ParsedAddress.builder()
                .postalAddress2(POSTAL_ADDRESS2)
                .build(),
            POSTAL_ADDRESS2
        ),
        Arguments.of(
            ParsedAddress.builder()
                .careOf(CARE_OF)
                .postalAddress1(POSTAL_ADDRESS1)
                .build(),
            CARE_OF + ", " + POSTAL_ADDRESS1
        ),
        Arguments.of(
            ParsedAddress.builder()
                .careOf(CARE_OF)
                .postalAddress1(POSTAL_ADDRESS1)
                .postalAddress2(POSTAL_ADDRESS2)
                .build(),
            CARE_OF + ", " + POSTAL_ADDRESS1 + ", " + POSTAL_ADDRESS2
        )
    );
  }

  @ParameterizedTest
  @MethodSource("provideAddressCombinations")
  void isBlank_ShouldReturnTrueForNullOrBlankStrings(ParsedAddress input, String expected) {
    assertEquals(expected, convert(input));
  }
}