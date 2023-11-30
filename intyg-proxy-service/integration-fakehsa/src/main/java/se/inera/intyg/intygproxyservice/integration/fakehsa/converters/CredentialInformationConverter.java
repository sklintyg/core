package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Commission;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HsaSystemRole;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation.ParsedCommission;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.ParsedPaTitle;

@Component
public class CredentialInformationConverter {

  public CredentialInformation convert(ParsedCredentialInformation credentialInformation,
      ParsedHsaPerson hsaPerson, Map<String, ParsedCareProvider> careProviderMap,
      Map<String, ParsedCareUnit> careUnitMap) {
    return CredentialInformation.builder()
        .personHsaId(hsaPerson.getHsaId())
        .personalPrescriptionCode(hsaPerson.getPersonalPrescriptionCode())
        .paTitleCode(convertPaTitle(hsaPerson))
        .hsaSystemRole(convertSystemRole(hsaPerson))
        .commission(
            credentialInformation.getParsedCommissionList() != null ?
                convertCommisson(
                    careUnitMap,
                    careProviderMap,
                    credentialInformation
                ) : Collections.emptyList()
        )
        .build();
  }

  private List<Commission> convertCommisson(Map<String, ParsedCareUnit> careUnitMap,
      Map<String, ParsedCareProvider> careProviderMap,
      ParsedCredentialInformation credentialInformation) {
    final var filteredCommissonPurpose = credentialInformation.getParsedCommissionList().stream()
        .filter(isPresentInUnitMap(careUnitMap))
        .filter(hasCommissonPurpose())
        .toList();

    if (filteredCommissonPurpose.isEmpty()) {
      return Collections.emptyList();
    }

    final var commissionsList = new ArrayList<Commission>();

    for (ParsedCommission parsedCommission : credentialInformation.getParsedCommissionList()) {
      final var parsedCareUnit = careUnitMap.get(parsedCommission.getHealthCareUnitHsaId());

      if (!careProviderMap.containsKey(parsedCareUnit.getCareProviderHsaId())) {
        continue;
      }

      final var parsedCareProvider = careProviderMap.get(parsedCareUnit.getCareProviderHsaId());

      parsedCommission.getCommissionPurpose().forEach(purpose -> commissionsList.add(
              Commission.builder()
                  .commissionHsaId(credentialInformation.getHsaId())
                  .commissionPurpose(purpose)
                  .commissionName(credentialInformation.getGivenName())
                  .healthCareUnitHsaId(parsedCareUnit.getId())
                  .healthCareUnitName(parsedCareUnit.getName())
                  .healthCareUnitStartDate(parsedCareUnit.getStart())
                  .healthCareUnitEndDate(parsedCareUnit.getEnd())
                  .healthCareProviderOrgNo(parsedCareUnit.getHealthCareProviderOrgno())
                  .healthCareProviderHsaId(parsedCareProvider.getId())
                  .healthCareProviderName(parsedCareProvider.getName())
                  .build()
          )
      );
    }

    return commissionsList;
  }

  private static Predicate<ParsedCommission> isPresentInUnitMap(
      Map<String, ParsedCareUnit> careUnitMap) {
    return parsedCommission -> careUnitMap.containsKey(parsedCommission.getHealthCareUnitHsaId());
  }

  private static Predicate<ParsedCommission> hasCommissonPurpose() {
    return parsedCommission -> parsedCommission.getCommissionPurpose() != null
        && !parsedCommission.getCommissionPurpose().isEmpty();
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
