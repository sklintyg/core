package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SickLeaveProvider;

@ExtendWith(MockitoExtension.class)
class GetSickLeaveCertificateDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  @Mock
  CertificateRepository certificateRepository;
  @InjectMocks
  GetSickLeaveCertificateDomainService getSickLeaveCertificateDomainService;

  @Test
  void shouldReturnOptionalEmptyIfNoSickLeaveProviderIsPresent() {
    final var certificate = MedicalCertificate.builder()
        .certificateModel(
            CertificateModel.builder().build()
        )
        .build();

    when(certificateRepository.getById(CERTIFICATE_ID))
        .thenReturn(certificate);

    final var result = getSickLeaveCertificateDomainService.get(CERTIFICATE_ID);
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnOptionalOfSickLeaveCertificateBuiltFromSickLeaveProvider() {
    final var expectedSickLeaveCertificate = SickLeaveCertificate.builder().build();
    final var sickLeaveProvider = mock(SickLeaveProvider.class);
    final var certificate = MedicalCertificate.builder()
        .certificateModel(
            CertificateModel.builder()
                .sickLeaveProvider(sickLeaveProvider)
                .build()
        )
        .build();

    when(certificateRepository.getById(CERTIFICATE_ID))
        .thenReturn(certificate);
    when(sickLeaveProvider.build(certificate))
        .thenReturn(Optional.of(expectedSickLeaveCertificate));

    final var result = getSickLeaveCertificateDomainService.get(CERTIFICATE_ID);
    assertEquals(Optional.of(expectedSickLeaveCertificate), result);
  }
}