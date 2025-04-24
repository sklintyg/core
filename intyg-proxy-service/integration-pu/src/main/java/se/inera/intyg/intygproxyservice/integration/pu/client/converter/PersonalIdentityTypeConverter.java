package se.inera.intyg.intygproxyservice.integration.pu.client.converter;

import lombok.extern.slf4j.Slf4j;
import se.inera.intyg.intygproxyservice.integration.api.pu.PersonId;
import se.riv.strategicresourcemanagement.persons.person.v3.IIType;

@Slf4j
public class PersonalIdentityTypeConverter {

  private PersonalIdentityTypeConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static PersonId personId(IIType iiType) {
    if (iiType == null) {
      log.warn("Unable to build personId - IIType is null");
      return PersonId.of(null);
    }
    return PersonId.of(iiType.getExtension());
  }
}