package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

@Repository
@RequiredArgsConstructor
public class FakeHsaRepository {

  private final EmployeeConverter employeeConverter;
  private final HealthCareUnitMembersConverter healthCareUnitMembersConverter;
  private final HealthCareUnitConverter healthCareUnitConverter;
  private final Map<String, ParsedHsaPerson> hsaPersonMap = new HashMap<>();
  private final Map<String, ParsedCredentialInformation> credentialInformationMap = new HashMap<>();
  private final Map<String, ParsedCareProvider> careProviderMap = new HashMap<>();
  private final Map<String, ParsedCareUnit> careUnitMap = new HashMap<>();
  private final Map<String, ParsedSubUnit> subUnitMap = new HashMap<>();

  public Employee getEmployee(String id) {
    final var employee = hsaPersonMap.get(id);
    if (employee == null) {
      throw new IllegalArgumentException(String.format("Employee was not found, id: '%s'", id));
    }
    return employeeConverter.convert(employee);
  }

  public HealthCareUnitMembers getHealthCareUnitMembers(String id) {
    final var parsedCareUnit = careUnitMap.get(id);
    if (parsedCareUnit == null) {
      throw new IllegalArgumentException(String.format("Care unit was not found, id: '%s'", id));
    }
    return healthCareUnitMembersConverter.convert(parsedCareUnit);
  }

  public HealthCareUnit getHealthCareUnit(String id) {
    final var parsedSubUnit = subUnitMap.get(id);
    if (parsedSubUnit != null) {
      return healthCareUnitConverter.convert(parsedSubUnit);
    }
    final var parsedCareUnit = careUnitMap.get(id);
    if (parsedCareUnit == null) {
      throw new IllegalArgumentException(String.format("Unit was not found, id: '%s'", id));
    }
    return healthCareUnitConverter.convert(parsedCareUnit);
  }


  public void addParsedCareProvider(ParsedCareProvider parsedCareProvider) {
    if (parsedCareProvider == null) {
      return;
    }
    final var careProviderId = parsedCareProvider.getId();
    addKeyAndValueToMap(careProviderId, parsedCareProvider, careProviderMap);
    if (careProviderHasNoCareUnits(parsedCareProvider)) {
      return;
    }

    for (ParsedCareUnit parsedCareUnit : parsedCareProvider.getCareUnits()) {
      parsedCareUnit.setCareProviderHsaId(careProviderId);
      final var careUnitId = parsedCareUnit.getId();
      addKeyAndValueToMap(careUnitId, parsedCareUnit, careUnitMap);

      if (careUnitHasNoSubUnits(parsedCareUnit)) {
        continue;
      }

      for (ParsedSubUnit subUnit : parsedCareUnit.getSubUnits()) {
        subUnit.setParentHsaId(careUnitId);
        addKeyAndValueToMap(subUnit.getId(), subUnit, subUnitMap);
      }
    }
  }

  public void addParsedHsaPerson(ParsedHsaPerson parsedHsaPerson) {
    final var hsaId = parsedHsaPerson.getHsaId();
    final var personId = parsedHsaPerson.getPersonalIdentityNumber();
    addKeyAndValueToMap(hsaId, parsedHsaPerson, hsaPersonMap);
    addKeyAndValueToMap(personId, parsedHsaPerson, hsaPersonMap);
  }

  public void addParsedCredentialInformation(
      ParsedCredentialInformation parsedCredentialInformation) {
    final var hsaId = parsedCredentialInformation.getHsaId();
    addKeyAndValueToMap(hsaId, parsedCredentialInformation, credentialInformationMap);
  }

  private static boolean careUnitHasNoSubUnits(ParsedCareUnit parsedCareUnit) {
    return parsedCareUnit.getSubUnits() == null || parsedCareUnit.getSubUnits().isEmpty();
  }

  private static boolean careProviderHasNoCareUnits(ParsedCareProvider parsedCareProvider) {
    return parsedCareProvider.getCareUnits() == null || parsedCareProvider.getCareUnits()
        .isEmpty();
  }

  private static <T> void addKeyAndValueToMap(String id, T value, Map<String, T> map) {
    if (id != null && value != null && map != null && !map.containsKey(id)) {
      map.put(trimAllWhiteSpace(id), value);
    }
  }

  private static String trimAllWhiteSpace(String id) {
    return StringUtils.trimAllWhitespace(id.toUpperCase());
  }
}
