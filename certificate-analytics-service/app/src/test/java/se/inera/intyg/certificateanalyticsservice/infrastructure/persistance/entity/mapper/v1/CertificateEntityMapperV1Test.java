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
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.PatientEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UnitEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CareProviderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateTypeRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.PatientRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UnitRepository;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataMessages;

@ExtendWith(MockitoExtension.class)
class CertificateEntityMapperV1Test {

  @InjectMocks
  private CertificateEntityMapperV1 certificateEntityMapperV1;
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
  void shouldMapCertificateCorrectly() {
    final var message = TestDataMessages.certificate();
    final var expectedCareProvider = mock(CareProviderEntity.class);
    final var expectedUnit = mock(UnitEntity.class);
    final var expectedPatient = mock(PatientEntity.class);
    final var expectedCertificateType = mock(CertificateTypeEntity.class);

    when(certificateEntityRepository.findByCertificateId(message.getId())).thenReturn(
        Optional.empty());
    when(careProviderRepository.findOrCreate(message.getCareProviderId())).thenReturn(
        expectedCareProvider);
    when(unitRepository.findOrCreate(message.getUnitId())).thenReturn(expectedUnit);
    when(patientRepository.findOrCreate(message.getPatientId())).thenReturn(expectedPatient);
    when(certificateTypeRepository.findOrCreate(message.getType(),
        message.getTypeVersion())).thenReturn(
        expectedCertificateType);

    final var expected = CertificateEntity.builder()
        .certificateId(message.getId())
        .certificateType(expectedCertificateType)
        .patient(expectedPatient)
        .unit(expectedUnit)
        .careProvider(expectedCareProvider)
        .build();

    final var result = certificateEntityMapperV1.map(message);

    assertEquals(expected, result);
  }
}
