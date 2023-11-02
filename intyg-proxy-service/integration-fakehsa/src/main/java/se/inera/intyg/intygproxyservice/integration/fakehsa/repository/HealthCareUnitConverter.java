package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

@Component
public class HealthCareUnitConverter implements UnitConverter {

  @Override
  public HealthCareUnit convert(ParsedCareUnit parsedCareUnit) {
    return HealthCareUnit.builder()
        .healthCareUnitHsaId(parsedCareUnit.getId())
        .healthCareProviderStartDate(parsedCareUnit.getStart())
        .healthCareProviderEndDate(parsedCareUnit.getEnd())
        .healthCareUnitName(parsedCareUnit.getName())
        .unitIsHealthCareUnit(true)
        .healthCareProviderHsaId(parsedCareUnit.getCareProviderHsaId())
        .healthCareProviderOrgNo(parsedCareUnit.getHealthCareProviderOrgno())
        .build();
  }

  @Override
  public HealthCareUnit convert(ParsedSubUnit parsedSubUnit) {
    return HealthCareUnit.builder()
        .healthCareUnitMemberHsaId(parsedSubUnit.getId())
        .healthCareUnitMemberName(parsedSubUnit.getName())
        .healthCareUnitMemberStartDate(parsedSubUnit.getStart())
        .healthCareUnitMemberEndDate(parsedSubUnit.getEnd())
        .healthCareUnitHsaId(parsedSubUnit.getParentHsaId())
        .unitIsHealthCareUnit(false)
        .build();
  }
}
