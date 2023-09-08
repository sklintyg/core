package se.inera.intyg.intygproxyservice.integration.pu.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.CITY;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.DECEASED;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.GIVEN_NAME;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.MIDDLE_NAME;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.PERSON_ID_AS_PERSONNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.POSTAL_ADDRESS1;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.POSTAL_CODE;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.PROTECTED_PERSON;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.SURNAME;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.TEST_INDICATED;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.requestedPersonRecordType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetPersonsForProfileResponseTypeConverterTest {

  private GetPersonsForProfileResponseTypeConverter getPersonsForProfileReponseTypeConverter;

  @BeforeEach
  void setUp() {
    getPersonsForProfileReponseTypeConverter = new GetPersonsForProfileResponseTypeConverter();
  }

  @Test
  void shallIncludePersonnummer() {
    final var actualPerson = getPersonsForProfileReponseTypeConverter.convert(
        requestedPersonRecordType()
    );

    assertEquals(PERSON_ID_AS_PERSONNUMMER, actualPerson.getPersonnummer());
  }

  @Test
  void shallIncludeFornamn() {
    final var actualPerson = getPersonsForProfileReponseTypeConverter.convert(
        requestedPersonRecordType()
    );

    assertEquals(GIVEN_NAME, actualPerson.getFornamn());
  }

  @Test
  void shallIncludeMellannamn() {
    final var actualPerson = getPersonsForProfileReponseTypeConverter.convert(
        requestedPersonRecordType()
    );

    assertEquals(MIDDLE_NAME, actualPerson.getMellannamn());
  }

  @Test
  void shallIncludeEfternamn() {
    final var actualPerson = getPersonsForProfileReponseTypeConverter.convert(
        requestedPersonRecordType()
    );

    assertEquals(SURNAME, actualPerson.getEfternamn());
  }

  @Test
  void shallIncludePostAdress() {
    final var actualPerson = getPersonsForProfileReponseTypeConverter.convert(
        requestedPersonRecordType()
    );

    assertEquals(POSTAL_ADDRESS1, actualPerson.getPostadress());
  }

  @Test
  void shallIncludePostnummer() {
    final var actualPerson = getPersonsForProfileReponseTypeConverter.convert(
        requestedPersonRecordType()
    );

    assertEquals(POSTAL_CODE, actualPerson.getPostnummer());
  }

  @Test
  void shallIncludePostort() {
    final var actualPerson = getPersonsForProfileReponseTypeConverter.convert(
        requestedPersonRecordType()
    );

    assertEquals(CITY, actualPerson.getPostort());
  }

  @Test
  void shallIncludeTestIndicated() {
    final var actualPerson = getPersonsForProfileReponseTypeConverter.convert(
        requestedPersonRecordType()
    );

    assertEquals(TEST_INDICATED, actualPerson.isTestIndicator());
  }

  @Test
  void shallIncludeSekretessmarkering() {
    final var actualPerson = getPersonsForProfileReponseTypeConverter.convert(
        requestedPersonRecordType()
    );

    assertEquals(PROTECTED_PERSON, actualPerson.isSekretessmarkering());
  }

  @Test
  void shallIncludeAvliden() {
    final var actualPerson = getPersonsForProfileReponseTypeConverter.convert(
        requestedPersonRecordType()
    );

    assertEquals(DECEASED, actualPerson.isAvliden());
  }
}