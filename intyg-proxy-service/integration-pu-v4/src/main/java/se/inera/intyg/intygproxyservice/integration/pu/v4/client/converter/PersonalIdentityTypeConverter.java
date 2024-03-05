package se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter;


import se.riv.strategicresourcemanagement.persons.person.v4.IIType;

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
