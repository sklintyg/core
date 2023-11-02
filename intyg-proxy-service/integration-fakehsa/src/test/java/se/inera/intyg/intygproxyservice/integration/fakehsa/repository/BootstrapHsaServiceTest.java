package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BootstrapHsaServiceTest {

  private FakeHsaRepository fakeHsaRepository;
  private EmployeeConverter employeeConverter;
  private HealthCareUnitMembersConverter healthCareUnitMembersConverter;
  private HealthCareUnitConverter healthCareUnitConverter;
  private BootstrapHsaService bootstrapHsaService;

  @BeforeEach
  void setUp() {
    employeeConverter = new EmployeeConverter();
    healthCareUnitConverter = new HealthCareUnitConverter();
    healthCareUnitMembersConverter = new HealthCareUnitMembersConverter();
    fakeHsaRepository = new FakeHsaRepository(
        employeeConverter,
        healthCareUnitMembersConverter,
        healthCareUnitConverter
    );
    bootstrapHsaService = new BootstrapHsaService(
        fakeHsaRepository,
        new ObjectMapper()
    );

  }

  @Test
  void shallLoadEmployeeToRepository() throws IOException {
    bootstrapHsaService.bootstrapVardgivare();
    assertNotNull(fakeHsaRepository.getEmployee("TSTNMT2321000156-1079"));
  }

  @Test
  void shallLoadCareUnitToRepository() throws IOException {
    bootstrapHsaService.bootstrapVardgivare();
    assertNotNull(fakeHsaRepository.getHealthCareUnit("TSTNMT2321000156-ALVC"));
  }

  @Test
  void shallLoadSubCareUnitToRepository() throws IOException {
    bootstrapHsaService.bootstrapVardgivare();
    assertNotNull(fakeHsaRepository.getHealthCareUnit("TSTNMT2321000156-ALLM"));
  }
}
