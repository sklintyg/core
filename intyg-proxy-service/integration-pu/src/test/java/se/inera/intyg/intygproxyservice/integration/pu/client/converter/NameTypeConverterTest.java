package se.inera.intyg.intygproxyservice.integration.pu.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.GIVEN_NAME;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.MIDDLE_NAME;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.SURNAME;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.nameType;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.NameTypeConverter.givenName;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.NameTypeConverter.middleName;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.NameTypeConverter.surname;

import org.junit.jupiter.api.Test;
import se.riv.strategicresourcemanagement.persons.person.v3.NameType;

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