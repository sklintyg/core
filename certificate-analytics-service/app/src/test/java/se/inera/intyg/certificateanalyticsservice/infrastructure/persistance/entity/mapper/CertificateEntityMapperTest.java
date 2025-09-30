package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

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
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.PatientEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UnitEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CareProviderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateTypeRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.PatientRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UnitRepository;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataPseudonymized;

@ExtendWith(MockitoExtension.class)
class CertificateEntityMapperTest {

  @InjectMocks
  private CertificateEntityMapper certificateEntityMapper;
  @Mock
  private CareProviderRepository careProviderRepository;
  @Mock
  private UnitRepository unitRepository;
  @Mock
  private PatientRepository patientRepository;
  @Mock
  private CertificateTypeRepository certificateTypeRepository;
  @Mock
  private CertificateEntityRepository certificateEntityRepository;

  @Test
  void shouldMapCertificateCorrectlyWhenCreatingNewEntity() {
    final var message = TestDataPseudonymized.draftPseudonymizedMessageBuilder().build();
    final var expectedCareProvider = mock(CareProviderEntity.class);
    final var expectedUnit = mock(UnitEntity.class);
    final var expectedPatient = mock(PatientEntity.class);
    final var expectedCertificateType = mock(CertificateTypeEntity.class);
    final var newEntity = CertificateEntity.builder()
        .certificateId(message.getCertificateId())
        .certificateType(expectedCertificateType)
        .patient(expectedPatient)
        .unit(expectedUnit)
        .careProvider(expectedCareProvider)
        .build();

    when(certificateEntityRepository.findByCertificateId(message.getCertificateId())).thenReturn(
        Optional.empty());
    when(careProviderRepository.findOrCreate(message.getCertificateCareProviderId())).thenReturn(
        expectedCareProvider);
    when(unitRepository.findOrCreate(message.getCertificateUnitId())).thenReturn(expectedUnit);
    when(patientRepository.findOrCreate(message.getCertificatePatientId())).thenReturn(
        expectedPatient);
    when(certificateTypeRepository.findOrCreate(message.getCertificateType(),
        message.getCertificateTypeVersion())).thenReturn(expectedCertificateType);
    when(certificateEntityRepository.save(newEntity)).thenReturn(newEntity);

    final var result = certificateEntityMapper.map(message);

    assertEquals(newEntity, result);
  }

  @Test
  void shouldReturnExistingCertificateEntityWhenFound() {
    final var message = TestDataPseudonymized.draftPseudonymizedMessageBuilder().build();
    final var existingEntity = mock(CertificateEntity.class);
    when(certificateEntityRepository.findByCertificateId(message.getCertificateId())).thenReturn(
        Optional.of(existingEntity));

    final var result = certificateEntityMapper.map(message);
    assertEquals(existingEntity, result);
  }
}
