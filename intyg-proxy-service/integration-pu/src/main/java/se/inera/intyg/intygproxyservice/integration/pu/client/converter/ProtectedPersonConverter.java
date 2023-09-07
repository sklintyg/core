package se.inera.intyg.intygproxyservice.integration.pu.client.converter;

import se.riv.strategicresourcemanagement.persons.person.v3.PersonRecordType;

public class ProtectedPersonConverter {

  private ProtectedPersonConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean protectedPerson(PersonRecordType personRecord) {
    return personRecord.isProtectedPersonIndicator() || isProtectedPopulationRecord(personRecord);
  }

  private static Boolean isProtectedPopulationRecord(PersonRecordType personRecord) {
    return personRecord.isProtectedPopulationRecord() != null
        && personRecord.isProtectedPopulationRecord();
  }
}
