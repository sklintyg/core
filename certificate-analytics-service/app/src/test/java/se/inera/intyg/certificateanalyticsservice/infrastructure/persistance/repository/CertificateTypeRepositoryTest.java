package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE_VERSION;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.CertificateTypeEntityMapperV1;

@ExtendWith(MockitoExtension.class)
class CertificateTypeRepositoryTest {

  @InjectMocks
  private CertificateTypeRepository certificateTypeRepository;
  @Mock
  private CertificateTypeEntityRepository certificateTypeEntityRepository;

  @Test
  void shouldCreateNewCertificateTypeEntityIfNotExists() {
    final var typeEntity = CertificateTypeEntityMapperV1.map(CERTIFICATE_TYPE,
        CERTIFICATE_TYPE_VERSION);
    final var savedTypeEntity = mock(CertificateTypeEntity.class);
    when(certificateTypeEntityRepository.findByCertificateTypeAndCertificateTypeVersion(
        CERTIFICATE_TYPE, CERTIFICATE_TYPE_VERSION)).thenReturn(Optional.empty());
    when(certificateTypeEntityRepository.save(typeEntity)).thenReturn(savedTypeEntity);

    final var result = certificateTypeRepository.findOrCreate(CERTIFICATE_TYPE,
        CERTIFICATE_TYPE_VERSION);

    assertEquals(savedTypeEntity, result);
  }

  @Test
  void shouldFindExistingCertificateTypeEntity() {
    final var typeEntity = mock(CertificateTypeEntity.class);
    when(certificateTypeEntityRepository.findByCertificateTypeAndCertificateTypeVersion(
        CERTIFICATE_TYPE, CERTIFICATE_TYPE_VERSION)).thenReturn(Optional.of(typeEntity));

    final var result = certificateTypeRepository.findOrCreate(CERTIFICATE_TYPE,
        CERTIFICATE_TYPE_VERSION);

    assertEquals(typeEntity, result);
  }
}

