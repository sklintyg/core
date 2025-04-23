package se.inera.intyg.intygproxyservice.integration.pu.client.converter;

import se.inera.intyg.intygproxyservice.integration.api.pu.PersonId;
import se.riv.strategicresourcemanagement.persons.person.v3.IIType;

public class PersonalIdentityTypeConverter {

  private PersonalIdentityTypeConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static PersonId personId(IIType iiType) {
    if (iiType == null) {
      return null;
    }
    return PersonId.of(iiType.getExtension());
  }
}