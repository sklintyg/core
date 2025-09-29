package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CareProviderEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.PatientEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UnitEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UserEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CareProviderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.PatientRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UnitRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UserRepository;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataCertificateAnalyticsMessages;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;

@ExtendWith(MockitoExtension.class)
class CertificateEntityMapperV1Test {

  @InjectMocks
  private CertificateEntityMapperV1 certificateEntityMapperV1;
  @Mock
  private CareProviderRepository careProviderRepository;
  @Mock
  private UnitRepository unitRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private PatientRepository patientRepository;
  @Mock
  private CertificateEntityRepository certificateEntityRepository;

  @Test
  void shouldMapCertificateCorrectly() {
    final var certificateMsg = TestDataCertificateAnalyticsMessages.certificate();
    final var expectedCareProvider = mock(CareProviderEntity.class);
    final var expectedUnit = mock(UnitEntity.class);
    final var expectedUser = mock(UserEntity.class);
    final var expectedPatient = mock(PatientEntity.class);
    final var expectedCertificateType = TestDataEntities.certificateEntity().getCertificateType();

    when(certificateEntityRepository.findByCertificateId(certificateMsg.getId())).thenReturn(
        Optional.empty());
    when(careProviderRepository.findOrCreate(certificateMsg.getCareProviderId())).thenReturn(
        expectedCareProvider);
    when(unitRepository.findOrCreate(certificateMsg.getUnitId())).thenReturn(expectedUnit);
    when(userRepository.findOrCreate(certificateMsg.getStaffId())).thenReturn(expectedUser);
    when(patientRepository.findOrCreate(certificateMsg.getPatientId())).thenReturn(expectedPatient);

    final var expected = CertificateEntity.builder()
        .certificateId(certificateMsg.getId())
        .certificateType(expectedCertificateType)
        .staff(expectedUser)
        .patient(expectedPatient)
        .unit(expectedUnit)
        .careProvider(expectedCareProvider)
        .build();

    final var result = certificateEntityMapperV1.map(certificateMsg);

    assertEquals(expected, result);
  }
}
