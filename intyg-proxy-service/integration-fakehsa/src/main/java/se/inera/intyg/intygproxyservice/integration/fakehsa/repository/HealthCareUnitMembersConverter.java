package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMember;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

@Component
public class HealthCareUnitMembersConverter {

  private static final String NOT_FOUND = "-finns-ej";

  public HealthCareUnitMembers convert(ParsedCareUnit parsedCareUnit) {
    return HealthCareUnitMembers.builder()
        .healthCareUnitPrescriptionCode(toList(parsedCareUnit.getPrescriptionCode()))
        .healthCareUnitMember(getHealthCareUnitMember(parsedCareUnit))
        .build();
  }

  private List<HealthCareUnitMember> getHealthCareUnitMember(ParsedCareUnit parsedCareUnit) {
    if (hasSubUnits(parsedCareUnit.getSubUnits())) {
      return getHealthCareUnitMembers(parsedCareUnit.getSubUnits());
    }
    return Collections.emptyList();
  }

  private static boolean hasSubUnits(List<ParsedSubUnit> parsedCareUnit) {
    return parsedCareUnit != null && !parsedCareUnit.isEmpty();
  }

  private static List<String> toList(String value) {
    if (value == null || value.isEmpty()) {
      return Collections.emptyList();
    }
    return List.of(value);
  }

  private List<HealthCareUnitMember> getHealthCareUnitMembers(List<ParsedSubUnit> subUnits) {
    return subUnits.stream()
        .filter(removeInvalidUnits())
        .map(subUnit -> HealthCareUnitMember.builder()
            .healthCareUnitMemberHsaId(subUnit.getId())
            .healthCareUnitMemberName(subUnit.getName())
            .healthCareUnitMemberStartDate(subUnit.getStart())
            .healthCareUnitMemberEndDate(subUnit.getEnd())
            .healthCareUnitMemberpostalAddress(toList(subUnit.getPostalAddress()))
            .healthCareUnitMemberpostalCode(subUnit.getPostalCode())
            .healthCareUnitMemberPrescriptionCode(toList(subUnit.getPrescriptionCode()))
            .build())
        .toList();
  }

  private static Predicate<ParsedSubUnit> removeInvalidUnits() {
    return subUnit -> !subUnit.getId().endsWith(NOT_FOUND);
  }
}
