package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;

@Component
@RequiredArgsConstructor
public class CredentialsForPersonConverter {

  private final RestrictionConverter restrictionConverter;

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
                .collect(Collectors.toList())
        )
        .build();
  }
}
