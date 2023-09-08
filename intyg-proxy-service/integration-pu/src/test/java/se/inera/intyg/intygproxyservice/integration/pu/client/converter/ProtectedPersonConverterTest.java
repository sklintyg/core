package se.inera.intyg.intygproxyservice.integration.pu.client.converter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.ProtectedPersonConverter.protectedPerson;

import org.junit.jupiter.api.Test;
import se.riv.strategicresourcemanagement.persons.person.v3.PersonRecordType;

class ProtectedPersonConverterTest {

  @Test
  void shallReturnTrueIfProtectedPersonIsTrue() {
    final var personRecordType = new PersonRecordType();
    personRecordType.setProtectedPersonIndicator(true);
    assertTrue(
        protectedPerson(personRecordType)
    );
  }

  @Test
  void shallReturnTrueIfProtectedPopulationIsTrue() {
    final var personRecordType = new PersonRecordType();
    personRecordType.setProtectedPopulationRecord(true);
    assertTrue(
        protectedPerson(personRecordType)
    );
  }

  @Test
  void shallReturnFalseIfNeitherProtectedPersonOrProtectedPopulationIsTrue() {
    final var personRecordType = new PersonRecordType();
    personRecordType.setProtectedPopulationRecord(false);
    assertFalse(
        protectedPerson(personRecordType)
    );
  }

  @Test
  void shallReturnFalseIfNeitherProtectedPersonIsFalseAndProtectedPopulationIsNull() {
    final var personRecordType = new PersonRecordType();
    assertFalse(
        protectedPerson(personRecordType)
    );
  }

  @Test
  void shallReturnFalseIfBothProtectedPersonOrProtectedPopulationIsTrue() {
    final var personRecordType = new PersonRecordType();
    personRecordType.setProtectedPersonIndicator(true);
    personRecordType.setProtectedPopulationRecord(true);
    assertTrue(
        protectedPerson(personRecordType)
    );
  }
}