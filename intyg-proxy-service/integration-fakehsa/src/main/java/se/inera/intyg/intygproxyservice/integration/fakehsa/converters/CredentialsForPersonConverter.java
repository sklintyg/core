package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;

@Component
@RequiredArgsConstructor
public class CredentialsForPersonConverter {

  private final RestrictionConverter restrictionConverter;
  private final SpecialitiesConverter specialitiesConverter;
  private final ProfessionalLicenceTypeConverter licenceTypeConverter;

  public CredentialsForPerson convert(ParsedHsaPerson parsedHsaPerson) {
    if (parsedHsaPerson == null) {
      return CredentialsForPerson.builder().build();
    }

    return CredentialsForPerson.builder()
        .personalIdentityNumber(parsedHsaPerson.getPersonalIdentityNumber())
        .personalPrescriptionCode(parsedHsaPerson.getPersonalPrescriptionCode())
        .educationCode(parsedHsaPerson.getEducationCodes())
        .restrictions(
            parsedHsaPerson.getRestrictions().stream()
                .map(restrictionConverter::convert)
                .toList()
        )
        .healthCareProfessionalLicenceSpeciality(
            parsedHsaPerson.getSpecialities().stream()
                .map(specialitiesConverter::convert)
                .toList()
        )
        .healthCareProfessionalLicence(
            parsedHsaPerson.getHealthCareProfessionalLicenceType().stream()
                .map(licenceTypeConverter::convert)
                .toList()
        )
        .build();
  }
}
