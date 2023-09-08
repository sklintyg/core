package se.inera.intyg.intygproxyservice.integration.pu.client.converter;

import se.riv.strategicresourcemanagement.persons.person.v3.IIType;

public class PersonalIdentityTypeConverter {

  private PersonalIdentityTypeConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static String extension(IIType iiType) {
    if (iiType == null) {
      return null;
    }
    return iiType.getExtension();
  }
}
