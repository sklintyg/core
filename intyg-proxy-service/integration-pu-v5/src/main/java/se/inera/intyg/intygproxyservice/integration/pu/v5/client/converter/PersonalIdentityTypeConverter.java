package se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter;


import se.inera.intyg.intygproxyservice.integration.api.pu.PersonId;
import se.riv.strategicresourcemanagement.persons.person.v5.IIType;

public class PersonalIdentityTypeConverter {

  private PersonalIdentityTypeConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static PersonId personId(IIType iiType) {
    if (iiType == null) {
      return PersonId.of(null);
    }
    return PersonId.of(iiType.getExtension());
  }
}