package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Commission;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation.ParsedCommission;

@Component
public class CommissonListConverter {

  public List<Commission> convert(Map<String, ParsedCareUnit> careUnitMap,
      Map<String, ParsedCareProvider> careProviderMap,
      ParsedCredentialInformation credentialInformation) {
    if (credentialInformation.getParsedCommissionList() == null) {
      return Collections.emptyList();
    }

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

}
