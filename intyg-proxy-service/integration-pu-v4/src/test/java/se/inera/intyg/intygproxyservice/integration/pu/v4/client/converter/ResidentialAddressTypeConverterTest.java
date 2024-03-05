package se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.CARE_OF;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.CITY;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.POSTAL_ADDRESS1;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.POSTAL_ADDRESS2;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.POSTAL_CODE;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.addressInformationType;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter.ResidentialAddressTypeConverter.city;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter.ResidentialAddressTypeConverter.postalAddress;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter.ResidentialAddressTypeConverter.postalCode;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.riv.strategicresourcemanagement.persons.person.v4.AddressInformationType;
import se.riv.strategicresourcemanagement.persons.person.v4.ResidentialAddressType;

class ResidentialAddressTypeConverterTest {

  private static Stream<Arguments> provideAddressCombinations() {
    return Stream.of(
        Arguments.of(
            addressInformationType(CARE_OF, null, null),
            CARE_OF
        ),
        Arguments.of(
            addressInformationType(null, POSTAL_ADDRESS1, null),
            POSTAL_ADDRESS1
        ),
        Arguments.of(
            addressInformationType(null, null, POSTAL_ADDRESS2),
            POSTAL_ADDRESS2
        ),
        Arguments.of(
            addressInformationType(CARE_OF, POSTAL_ADDRESS1, null),
            CARE_OF + ", " + POSTAL_ADDRESS1
        ),
        Arguments.of(
            addressInformationType(CARE_OF, POSTAL_ADDRESS1, POSTAL_ADDRESS2),
            CARE_OF + ", " + POSTAL_ADDRESS1 + ", " + POSTAL_ADDRESS2
        )
    );
  }

  @ParameterizedTest
  @MethodSource("provideAddressCombinations")
  void shouldReturnPostalAddress(AddressInformationType input, String expected) {
    assertEquals(expected, postalAddress(input));
  }

  @Test
  void shallReturnPostalAddressNullIfAddressInformationTypeIsNull() {
    assertNull(postalAddress(null));
  }

  @Test
  void shallReturnPostalAddressNullIfResidentialAddressIsNull() {
    assertNull(postalAddress(new AddressInformationType()));
  }

  @Test
  void shallReturnPostalCode() {
    assertEquals(POSTAL_CODE, postalCode(addressInformationType()));
  }

  @Test
  void shallReturnPostalCodeNullIfAddressInformationTypeIsNull() {
    assertNull(postalCode(null));
  }

  @Test
  void shallReturnPostalCodeNullIfResidentialAddressIsNull() {
    assertNull(postalCode(new AddressInformationType()));
  }

  @Test
  void shallReturnPostalCodeNullIfResidentialAddressPostalCodeIsNull() {
    final var addressInformationType = new AddressInformationType();
    addressInformationType.setResidentialAddress(new ResidentialAddressType());
    assertNull(postalCode(addressInformationType));
  }

  @Test
    // This test is important because PU can return 0 instead of null if postalCode is missing
  void shallReturnPostalCodeNullIfResidentialAddressPostalCodeIsZero() {
    final var addressInformationType = new AddressInformationType();
    addressInformationType.setResidentialAddress(new ResidentialAddressType());
    addressInformationType.getResidentialAddress().setPostalCode(0);
    assertNull(postalCode(addressInformationType));
  }

  @Test
  void shallReturnCity() {
    assertEquals(CITY, city(addressInformationType()));
  }

  @Test
  void shallReturnCityNullIfAddressInformationTypeIsNull() {
    assertNull(city(null));
  }

  @Test
  void shallReturnCityNullIfResidentialAddressIsNull() {
    assertNull(city(new AddressInformationType()));
  }
}