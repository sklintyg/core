package se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter;


import se.riv.strategicresourcemanagement.persons.person.v4.PersonRecordType;

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
