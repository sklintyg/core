package se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.GIVEN_NAME;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.MIDDLE_NAME;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.SURNAME;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.nameType;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter.NameTypeConverter.givenName;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter.NameTypeConverter.middleName;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter.NameTypeConverter.surname;

import org.junit.jupiter.api.Test;
import se.riv.strategicresourcemanagement.persons.person.v4.NameType;

class NameTypeConverterTest {

  @Test
  void shallReturnGivenNameIfExists() {
    assertEquals(GIVEN_NAME, givenName(nameType()));
  }

  @Test
  void shallReturnNullGivenNameIfNameTypeNull() {
    assertNull(givenName(null));
  }

  @Test
  void shallReturnNullGivenNameIfGivenNameNull() {
    assertNull(givenName(new NameType()));
  }

  @Test
  void shallReturnMiddleNameIfExists() {
    assertEquals(MIDDLE_NAME, middleName(nameType()));
  }

  @Test
  void shallReturnNullMiddleNameIfNameTypeNull() {
    assertNull(middleName(null));
  }

  @Test
  void shallReturnNullMiddleNameIfMiddleNameNull() {
    assertNull(middleName(new NameType()));
  }

  @Test
  void shallReturnSurnameIfExists() {
    assertEquals(SURNAME, surname(nameType()));
  }

  @Test
  void shallReturnNullSurnameIfNameTypeNull() {
    assertNull(surname(null));
  }

  @Test
  void shallReturnNullSurnameIfSurnameNull() {
    assertNull(surname(new NameType()));
  }
}