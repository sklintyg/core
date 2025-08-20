package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.CITY;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.DECEASED;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.GIVEN_NAME;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.IS_ACTIVE;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.MIDDLE_NAME;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.PARSED_PERSON;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.PERSON_ID;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.POSTAL_ADDRESS2;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.POSTAL_CODE;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.PROTECTED_PERSON;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.SURNAME;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.TEST_INDICATED;
import static se.inera.intyg.intygproxyservice.integration.fakepu.repository.PersonConverter.convert;

import org.junit.jupiter.api.Test;

class PersonConverterTest {

  @Test
  void shallIncludePersonnummer() {
    assertEquals(PERSON_ID, convert(PARSED_PERSON).getPersonnummer().id());
  }

  @Test
  void shallIncludeFornamn() {
    assertEquals(GIVEN_NAME, convert(PARSED_PERSON).getFornamn());
  }

  @Test
  void shallIncludeMellannamn() {
    assertEquals(MIDDLE_NAME, convert(PARSED_PERSON).getMellannamn());
  }

  @Test
  void shallIncludeSurname() {
    assertEquals(SURNAME, convert(PARSED_PERSON).getEfternamn());
  }

  @Test
  void shallIncludePostadress() {
    assertEquals(POSTAL_ADDRESS2, convert(PARSED_PERSON).getPostadress());
  }

  @Test
  void shallIncludePostnummer() {
    assertEquals(POSTAL_CODE, convert(PARSED_PERSON).getPostnummer());
  }

  @Test
  void shallIncludePostort() {
    assertEquals(CITY, convert(PARSED_PERSON).getPostort());
  }

  @Test
  void shallIncludeAvliden() {
    assertEquals(DECEASED, convert(PARSED_PERSON).isAvliden());
  }

  @Test
  void shallIncludeSekretessmarking() {
    assertEquals(PROTECTED_PERSON, convert(PARSED_PERSON).isSekretessmarkering());
  }

  @Test
  void shallIncludeTestIndicator() {
    assertEquals(TEST_INDICATED, convert(PARSED_PERSON).isTestIndicator());
  }

  @Test
  void shallIncludeIsActive() {
    assertEquals(IS_ACTIVE, convert(PARSED_PERSON).isActive());
  }
}