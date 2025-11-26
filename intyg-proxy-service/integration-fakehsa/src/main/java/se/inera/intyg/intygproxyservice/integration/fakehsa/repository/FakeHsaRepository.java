package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareProvider;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.CareProviderConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.CredentialInformationConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.CredentialsForPersonConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.EmployeeConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.HealthCareUnitConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.HealthCareUnitMembersConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.UnitConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FakeHsaRepository {

  private final EmployeeConverter employeeConverter;
  private final HealthCareUnitMembersConverter healthCareUnitMembersConverter;
  private final HealthCareUnitConverter healthCareUnitConverter;
  private final UnitConverter unitConverter;
  private final CredentialInformationConverter credentialInformationConverter;
  private final CareProviderConverter careProviderConverter;

  private final CredentialsForPersonConverter credentialsForPersonConverter;
  private final Map<String, ParsedHsaPerson> hsaPersonMap = new HashMap<>();
  private final Map<String, ParsedCredentialInformation> credentialInformationMap = new HashMap<>();
  private final Map<String, ParsedCareProvider> careProviderMap = new HashMap<>();
  private final Map<String, ParsedCareUnit> careUnitMap = new HashMap<>();
  private final Map<String, ParsedSubUnit> subUnitMap = new HashMap<>();


  public Employee getEmployee(String id) {
    final var employee = hsaPersonMap.get(id);
    if (employee == null) {
      log.info("Employee was not found, id: '{}'", id);
      return Employee.builder()
          .personInformation(Collections.emptyList())
          .build();
    }
    return employeeConverter.convert(employee);
  }

  public HealthCareUnitMembers getHealthCareUnitMembers(String id) {
    final var parsedCareUnit = careUnitMap.get(id);
    if (parsedCareUnit == null) {
      return HealthCareUnitMembers.builder().build();
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
      log.info("Unit was not found, id: '{}'", id);
      return HealthCareUnit.builder().build();
    }
    return healthCareUnitConverter.convert(parsedCareUnit);
  }

  public List<HealthCareProvider> getHealthCareProvider(String id) {
    final var parsedProvider = careProviderMap.get(id);

    if (parsedProvider == null) {
      return Collections.emptyList();
    }

    return List.of(careProviderConverter.convert(parsedProvider));
  }

  public List<CredentialInformation> getCredentialInformation(String personHsaId) {
    final var parsedCredentialInformation = credentialInformationMap.get(personHsaId);
    final var parsedHsaPerson = hsaPersonMap.get(personHsaId);

    if (parsedCredentialInformation == null || parsedHsaPerson == null) {
      return Collections.emptyList();
    }

    final var credentialInformation = credentialInformationConverter.convert(
        parsedCredentialInformation,
        parsedHsaPerson,
        careProviderMap,
        careUnitMap
    );

    return List.of(credentialInformation);
  }

  public Unit getUnit(String id) {
    final var parsedCareProvider = careProviderMap.get(id);

    if (parsedCareProvider != null) {
      return Unit.builder()
          .unitName(parsedCareProvider.getName())
          .unitHsaId(parsedCareProvider.getId())
          .build();
    }

    final var parsedCareUnit = careUnitMap.get(id);
    if (parsedCareUnit != null) {
      return unitConverter.convert(parsedCareUnit);
    }

    final var parsedSubUnit = subUnitMap.get(id);
    if (parsedSubUnit != null) {
      return unitConverter.convert(parsedSubUnit);
    }

    return null;
  }

  public LocalDateTime getLastUpdate() {
    return LocalDateTime.now(ZoneId.systemDefault());
  }

  public CredentialsForPerson getCredentialsForPerson(
      String personId) {
    final var parsedHsaPerson = hsaPersonMap.get(personId);
    return credentialsForPersonConverter.convert(parsedHsaPerson);
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
