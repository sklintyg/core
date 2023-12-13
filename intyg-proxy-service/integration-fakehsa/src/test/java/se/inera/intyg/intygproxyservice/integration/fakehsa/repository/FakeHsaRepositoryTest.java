package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareProvider;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.CareProviderConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.CredentialInformationConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.EmployeeConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.HealthCareUnitConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.HealthCareUnitMembersConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.converters.UnitConverter;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

@ExtendWith(MockitoExtension.class)
class FakeHsaRepositoryTest {

  private static final String UNIT_NAME = "unitName";
  @Mock
  private EmployeeConverter employeeConverter;
  @Mock
  private HealthCareUnitConverter healthCareUnitConverter;
  @Mock
  private HealthCareUnitMembersConverter healthCareUnitMembersConverter;
  @Mock
  private CareProviderConverter careProviderConverter;
  @Mock
  private CredentialInformationConverter credentialInformationConverter;
  @Mock
  private UnitConverter unitConverter;
  @InjectMocks
  private FakeHsaRepository fakeHsaRepository;

  private static final String HSA_ID = "HSAID";
  private static final String SUB_UNIT_HSA_ID = "SUBUNITHSAID";
  private static final String HSA_ID_NOT_UPPERCASE = "hsaid";
  private static final String PERSON_ID = "PERSONID";

  @Test
  void shouldPutIdAsUpperCaseInMap() {
    final var parsedHsaPerson = ParsedHsaPerson.builder().hsaId(HSA_ID_NOT_UPPERCASE).build();
    final var expectedEmployee = Employee.builder().build();

    fakeHsaRepository.addParsedHsaPerson(parsedHsaPerson);
    when(employeeConverter.convert(parsedHsaPerson)).thenReturn(expectedEmployee);

    final var employee = fakeHsaRepository.getEmployee(HSA_ID);
    assertEquals(expectedEmployee, employee);
  }

  @Nested
  class GetEmployee {

    @Test
    void shouldReturnEmployeeAddedByHsaId() {
      final var parsedHsaPerson = ParsedHsaPerson.builder().hsaId(HSA_ID).build();
      final var expectedEmployee = Employee.builder().build();

      fakeHsaRepository.addParsedHsaPerson(parsedHsaPerson);
      when(employeeConverter.convert(parsedHsaPerson)).thenReturn(expectedEmployee);

      final var result = fakeHsaRepository.getEmployee(HSA_ID);
      assertEquals(expectedEmployee, result);
    }

    @Test
    void shouldReturnEmployeeAddedByPersonId() {
      final var parsedHsaPerson = ParsedHsaPerson.builder().personalIdentityNumber(PERSON_ID)
          .build();
      final var expectedEmployee = Employee.builder().build();

      fakeHsaRepository.addParsedHsaPerson(parsedHsaPerson);
      when(employeeConverter.convert(parsedHsaPerson)).thenReturn(expectedEmployee);

      final var result = fakeHsaRepository.getEmployee(PERSON_ID);
      assertEquals(expectedEmployee, result);
    }

    @Test
    void shouldThrowIlligalArgumentExceptionIfEmployeeNotFound() {
      final var parsedHsaPerson = ParsedHsaPerson.builder().personalIdentityNumber(PERSON_ID)
          .build();

      fakeHsaRepository.addParsedHsaPerson(parsedHsaPerson);
      assertThrows(IllegalArgumentException.class, () -> fakeHsaRepository.getEmployee(HSA_ID));
    }
  }

  @Nested
  class GetHealthCareUnitMembers {

    @Test
    void shouldReturnHealthCareUnitMembers() {
      final var parsedCareUnit = ParsedCareUnit.builder().id(HSA_ID).build();
      final var parsedCareProvider = ParsedCareProvider.builder().id(HSA_ID)
          .careUnits(List.of(parsedCareUnit))
          .build();
      final var expectedHealthCareUnitMembers = HealthCareUnitMembers.builder().build();

      fakeHsaRepository.addParsedCareProvider(parsedCareProvider);
      when(healthCareUnitMembersConverter.convert(parsedCareUnit)).thenReturn(
          expectedHealthCareUnitMembers);

      final var result = fakeHsaRepository.getHealthCareUnitMembers(HSA_ID);
      assertEquals(expectedHealthCareUnitMembers, result);
    }


    @Test
    void shouldThrowIlligalArgumentExceptionIfCareUnitNotFound() {
      final var parsedCareProvider = ParsedCareProvider.builder().id(HSA_ID).build();
      fakeHsaRepository.addParsedCareProvider(parsedCareProvider);
      assertThrows(IllegalArgumentException.class,
          () -> fakeHsaRepository.getHealthCareUnitMembers(HSA_ID));
    }
  }

  @Nested
  class GetHealthCareUnit {

    @Test
    void shouldReturnHealthCareUnitFromUnitMap() {
      final var parsedCareUnit = ParsedCareUnit.builder().id(HSA_ID).build();
      final var parsedCareProvider = ParsedCareProvider.builder().id(HSA_ID)
          .careUnits(List.of(parsedCareUnit))
          .build();
      final var healthCareUnit = HealthCareUnit.builder().build();

      fakeHsaRepository.addParsedCareProvider(parsedCareProvider);
      when(healthCareUnitConverter.convert(parsedCareUnit)).thenReturn(
          healthCareUnit);

      final var result = fakeHsaRepository.getHealthCareUnit(HSA_ID);
      assertEquals(healthCareUnit, result);
    }

    @Test
    void shouldReturnHealthCareUnitFromSubUnitMap() {
      final var parsedSubUnit = ParsedSubUnit.builder().id(SUB_UNIT_HSA_ID).build();
      final var parsedCareUnit = ParsedCareUnit.builder()
          .id(HSA_ID)
          .subUnits(
              List.of(parsedSubUnit)
          )
          .build();
      final var parsedCareProvider = ParsedCareProvider.builder().id(HSA_ID)
          .careUnits(List.of(parsedCareUnit))
          .build();
      final var healthCareUnit = HealthCareUnit.builder().build();

      fakeHsaRepository.addParsedCareProvider(parsedCareProvider);
      when(healthCareUnitConverter.convert(parsedSubUnit)).thenReturn(
          healthCareUnit);

      final var result = fakeHsaRepository.getHealthCareUnit(SUB_UNIT_HSA_ID);
      assertEquals(healthCareUnit, result);
    }


    @Test
    void shouldThrowIlligalArgumentExceptionIfUnitNotFound() {
      final var parsedCareProvider = ParsedCareProvider.builder().id(HSA_ID).build();
      fakeHsaRepository.addParsedCareProvider(parsedCareProvider);
      assertThrows(IllegalArgumentException.class,
          () -> fakeHsaRepository.getHealthCareUnit(HSA_ID));
    }
  }

  @Nested
  class GetUnit {

    @Test
    void shouldReturnUnitFromCareproviderMap() {
      final var expectedUnit = Unit.builder()
          .unitHsaId(HSA_ID)
          .unitName(UNIT_NAME)
          .build();

      final var parsedCareProvider = ParsedCareProvider.builder()
          .id(HSA_ID)
          .name(UNIT_NAME)
          .build();

      fakeHsaRepository.addParsedCareProvider(parsedCareProvider);

      final var result = fakeHsaRepository.getUnit(HSA_ID);
      assertEquals(expectedUnit, result);
    }

    @Test
    void shouldReturnUnitFromCareUnitMap() {
      final var expectedUnit = Unit.builder()
          .unitHsaId(HSA_ID)
          .unitName(UNIT_NAME)
          .build();

      final var parsedCareUnit = ParsedCareUnit.builder()
          .id(HSA_ID)
          .name(UNIT_NAME)
          .build();

      final var parsedCareProvider = ParsedCareProvider.builder()
          .careUnits(
              List.of(parsedCareUnit)
          )
          .build();

      fakeHsaRepository.addParsedCareProvider(parsedCareProvider);

      when(unitConverter.convert(parsedCareUnit)).thenReturn(expectedUnit);

      final var result = fakeHsaRepository.getUnit(HSA_ID);
      assertEquals(expectedUnit, result);
    }

    @Test
    void shouldReturnUnitFromSubUnitMap() {
      final var expectedUnit = Unit.builder()
          .unitHsaId(HSA_ID)
          .unitName(UNIT_NAME)
          .build();

      final var parsedSubUnit = ParsedSubUnit.builder()
          .id(HSA_ID)
          .name(UNIT_NAME)
          .build();

      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(parsedSubUnit)
          )
          .build();

      final var parsedCareProvider = ParsedCareProvider.builder()
          .careUnits(
              List.of(parsedCareUnit)
          )
          .build();

      fakeHsaRepository.addParsedCareProvider(parsedCareProvider);

      when(unitConverter.convert(parsedSubUnit)).thenReturn(expectedUnit);

      final var result = fakeHsaRepository.getUnit(HSA_ID);
      assertEquals(expectedUnit, result);
    }
  }

  @Nested
  class GetCredentialInformation {

    @Test
    void shouldReturnEmptyListIfHsaPersonNotFound() {
      fakeHsaRepository.addParsedCredentialInformation(
          ParsedCredentialInformation.builder()
              .hsaId(HSA_ID)
              .build()
      );
      final var result = fakeHsaRepository.getCredentialInformation(HSA_ID);

      assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListIfCredentialInformationNotFound() {
      fakeHsaRepository.addParsedHsaPerson(
          ParsedHsaPerson.builder()
              .hsaId(HSA_ID)
              .build()
      );
      final var result = fakeHsaRepository.getCredentialInformation(HSA_ID);

      assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnListOfCredentialInformation() {
      final var parsedCredentialInformation = ParsedCredentialInformation.builder()
          .hsaId(HSA_ID)
          .build();

      final var parsedHsaPerson = ParsedHsaPerson.builder()
          .hsaId(HSA_ID)
          .build();

      fakeHsaRepository.addParsedHsaPerson(
          parsedHsaPerson
      );

      fakeHsaRepository.addParsedCredentialInformation(
          parsedCredentialInformation
      );

      final var credentialInformation = CredentialInformation.builder()
          .personHsaId(HSA_ID)
          .build();

      when(credentialInformationConverter.convert(eq(parsedCredentialInformation),
          eq(parsedHsaPerson), anyMap(), anyMap())).thenReturn(
          credentialInformation
      );

      final var result = fakeHsaRepository.getCredentialInformation(HSA_ID);

      assertEquals(List.of(credentialInformation), result);
    }
  }

  @Nested
  class GetCareProvider {

    @Test
    void shouldReturnEmptyListIfNotFound() {
      final var result = fakeHsaRepository.getHealthCareProvider(HSA_ID);

      assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnListOfCareProvider() {
      final var parsed = ParsedCareProvider.builder()
          .id(HSA_ID)
          .build();
      fakeHsaRepository.addParsedCareProvider(
          parsed
      );
      final var expected = HealthCareProvider.builder()
          .healthCareProviderHsaId(HSA_ID)
          .build();

      when(careProviderConverter.convert(parsed))
          .thenReturn(expected);

      final var result = fakeHsaRepository.getHealthCareProvider(HSA_ID);

      assertEquals(List.of(expected), result);
    }
  }
}
