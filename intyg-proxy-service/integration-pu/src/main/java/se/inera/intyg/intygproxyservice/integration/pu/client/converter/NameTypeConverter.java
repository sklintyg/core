package se.inera.intyg.intygproxyservice.integration.pu.client.converter;

import se.riv.strategicresourcemanagement.persons.person.v3.NamePartType;
import se.riv.strategicresourcemanagement.persons.person.v3.NameType;

public class NameTypeConverter {

  private NameTypeConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static String givenName(NameType nameType) {
    if (nameType == null) {
      return null;
    }

    return name(nameType.getGivenName());
  }

  public static String middleName(NameType nameType) {
    if (nameType == null) {
      return null;
    }

    return name(nameType.getMiddleName());
  }

  public static String surname(NameType nameType) {
    if (nameType == null) {
      return null;
    }

    return name(nameType.getSurname());
  }

  private static String name(NamePartType namePartType) {
    if (namePartType == null) {
      return null;
    }
    return namePartType.getName();
  }
}
