package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_PARENT_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_PARENT_TYPE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE_VERSION;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CareProviderEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateRelationTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UnitEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CareProviderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateRelationEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateRelationTypeEntityRepository;
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
  private CertificateEntityRepository certificateEntityRepository;
  @Mock
  private CertificateRelationEntityRepository certificateRelationEntityRepository;
  @Mock
  private CertificateRelationTypeEntityRepository certificateRelationTypeEntityRepository;

  @Test
  void shouldMapCertificateCorrectlyWhenCreatingNewEntity() {
    final var message = TestDataPseudonymized.draftPseudonymizedMessageBuilder().build();
    final var expectedCareProvider = mock(CareProviderEntity.class);
    final var expectedUnit = mock(UnitEntity.class);
    final var newEntity = CertificateEntity.builder()
        .certificateId(message.getCertificateId())
        .certificateType(CERTIFICATE_TYPE)
        .certificateTypeVersion(CERTIFICATE_TYPE_VERSION)
        .unit(expectedUnit)
        .careProvider(expectedCareProvider)
        .build();

    when(certificateEntityRepository.findByCertificateId(message.getCertificateId())).thenReturn(
        Optional.empty());
    when(careProviderRepository.findOrCreate(message.getCertificateCareProviderId())).thenReturn(
        expectedCareProvider);
    when(unitRepository.findOrCreate(message.getCertificateUnitId())).thenReturn(expectedUnit);
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

  @Test
  void shouldCreateCertificateRelationIfParentIdAndTypePresentAndNotExists() {
    final var message = TestDataPseudonymized.draftPseudonymizedMessageBuilder()
        .certificateRelationParentId(CERTIFICATE_PARENT_ID)
        .certificateRelationParentType(CERTIFICATE_PARENT_TYPE)
        .build();
    final var parentEntity = mock(CertificateEntity.class);
    final var relationTypeEntity = CertificateRelationTypeEntity.builder()
        .relationType(CERTIFICATE_PARENT_TYPE)
        .build();
    final var newEntity = CertificateEntity.builder()
        .certificateId(message.getCertificateId())
        .build();

    when(certificateEntityRepository.findByCertificateId(message.getCertificateId())).thenReturn(
        Optional.of(newEntity));
    when(certificateEntityRepository.findByCertificateId(CERTIFICATE_PARENT_ID)).thenReturn(
        Optional.of(parentEntity));
    when(certificateRelationTypeEntityRepository
        .findByRelationType(CERTIFICATE_PARENT_TYPE))
        .thenReturn(Optional.of(relationTypeEntity));
    when(certificateRelationEntityRepository.findAll()).thenReturn(
        java.util.Collections.emptyList());

    certificateEntityMapper.map(message);

    verify(certificateRelationEntityRepository).save(
        argThat(
            rel -> rel.getParentCertificate().equals(parentEntity)
                && rel.getChildCertificate().equals(newEntity)
                && rel.getRelationType().equals(relationTypeEntity)
        )
    );
  }

  @Test
  void shouldNotCreateCertificateRelationIfParentIdOrTypeMissing() {
    final var message = TestDataPseudonymized.draftPseudonymizedMessageBuilder()
        .certificateRelationParentId(null)
        .certificateRelationParentType(null)
        .build();

    final var newEntity = CertificateEntity.builder()
        .certificateId(message.getCertificateId())
        .build();

    when(certificateEntityRepository.findByCertificateId(message.getCertificateId())).thenReturn(
        Optional.of(newEntity));

    certificateEntityMapper.map(message);

    verify(certificateRelationEntityRepository, never()).save(any());
  }
}
