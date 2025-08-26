package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.PlaceholderCertificate;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;

@ExtendWith(MockitoExtension.class)
class PlaceholderCertificateEntityMapperTest {


  @Mock
  CertificateEntityRepository certificateEntityRepository;
  @Mock
  CertificateModelEntityRepository certificateModelEntityRepository;
  @Mock
  PatientEntityRepository patientEntityRepository;
  @Mock
  StaffEntityRepository staffEntityRepository;
  @Mock
  UnitEntityRepository unitRepository;
  @InjectMocks
  PlaceholderCertificateEntityMapper certificateEntityMapper;

  @Test
  void shouldDoSomething() {
    certificateEntityMapper.toEntity(PlaceholderCertificate.builder().build());
  }
}