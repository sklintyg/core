package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

@ExtendWith(MockitoExtension.class)
class FakeHsaRepositoryTest {

  @Mock
  private EmployeeConverter employeeConverter;
  @Mock
  private HealthCareUnitConverter healthCareUnitConverter;
  @Mock
  private HealthCareUnitMembersConverter healthCareUnitMembersConverter;
  @Mock
  private HealthCareProviderConverter healthCareProviderConverter;
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
}
