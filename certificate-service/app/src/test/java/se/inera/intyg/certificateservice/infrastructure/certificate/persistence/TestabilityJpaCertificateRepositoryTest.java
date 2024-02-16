package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;

@ExtendWith(MockitoExtension.class)
class TestabilityJpaCertificateRepositoryTest {

  private static final CertificateEntity CERTIFICATE_ENTITY = CertificateEntity.builder().build();

  private static final Certificate CERTIFICATE = Certificate.builder().build();

  @Mock
  CertificateEntityRepository certificateEntityRepository;
  @Mock
  StaffEntityRepository staffEntityRepository;
  @Mock
  UnitEntityRepository unitEntityRepository;
  @Mock
  CertificateEntityMapper certificateEntityMapper;
  @InjectMocks
  TestabilityJpaCertificateRepository testabilityJpaCertificateRepository;

  @Test
  void shouldInsertCertificates() {
    doReturn(CERTIFICATE_ENTITY).when(certificateEntityMapper).toEntity(CERTIFICATE);
    doReturn(CERTIFICATE_ENTITY).when(certificateEntityRepository).save(CERTIFICATE_ENTITY);
    doReturn(CERTIFICATE).when(certificateEntityMapper).toDomain(CERTIFICATE_ENTITY);

    final var actualCertificate = testabilityJpaCertificateRepository.insert(CERTIFICATE);

    assertEquals(CERTIFICATE, actualCertificate);
  }

  @Test
  void shouldRemoveCertificates() {
    final var ids = List.of("ID1", "ID2");

    testabilityJpaCertificateRepository.remove(
        List.of(new CertificateId("ID1"), new CertificateId("ID2"))
    );

    verify(certificateEntityRepository).deleteAllByCertificateIdIn(ids);
  }
}