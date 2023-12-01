package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HsaSystemRole;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.ParsedPaTitle;

@Component
@RequiredArgsConstructor
public class CredentialInformationConverter {

  private final CommissonListConverter commissonListConverter;

  public CredentialInformation convert(ParsedCredentialInformation credentialInformation,
      ParsedHsaPerson hsaPerson, Map<String, ParsedCareProvider> careProviderMap,
      Map<String, ParsedCareUnit> careUnitMap) {
    return CredentialInformation.builder()
        .personHsaId(hsaPerson.getHsaId())
        .personalPrescriptionCode(hsaPerson.getPersonalPrescriptionCode())
        .paTitleCode(convertPaTitle(hsaPerson))
        .hsaSystemRole(convertSystemRole(hsaPerson))
        .commission(
            commissonListConverter.convert(careUnitMap, careProviderMap, credentialInformation)
        )
        .build();
  }

  private static List<HsaSystemRole> convertSystemRole(ParsedHsaPerson hsaPerson) {
    return hsaPerson.getSystemRoles() != null ? hsaPerson.getSystemRoles().stream()
        .map(role ->
            HsaSystemRole.builder()
                .role(role)
                .build()
        ).
        toList() : null;
  }

  private static List<String> convertPaTitle(ParsedHsaPerson hsaPerson) {
    return hsaPerson.getParsedPaTitle() != null ? hsaPerson.getParsedPaTitle().stream()
        .map(ParsedPaTitle::getTitleCode).toList() : null;
  }
}
