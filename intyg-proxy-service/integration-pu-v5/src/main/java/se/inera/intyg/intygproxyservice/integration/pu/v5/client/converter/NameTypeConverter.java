package se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter;


import se.riv.strategicresourcemanagement.persons.person.v5.NameType;

public class NameTypeConverter {

  private NameTypeConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static String givenName(NameType nameType) {
    if (nameType == null) {
      return null;
    }

    return nameType.getGivenName();
  }

  public static String middleName(NameType nameType) {
    if (nameType == null) {
      return null;
    }

    return nameType.getMiddleName();
  }

  public static String surname(NameType nameType) {
    if (nameType == null) {
      return null;
    }

    return nameType.getSurname();
  }
}
