package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SickLeaveProvider;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;

@ExtendWith(MockitoExtension.class)
class GetSickLeaveCertificatesDomainServiceTest {

  private static final CertificatesRequest SICK_LEAVES_REQUEST = CertificatesRequest.builder()
      .build();
  @Mock
  CertificateRepository certificateRepository;
  @InjectMocks
  GetSickLeaveCertificatesDomainService getSickLeaveCertificatesDomainService;

  @Test
  void shouldReturnEmptyListIfNoSickLeaveProviderIsPresent() {
    final var certificate = MedicalCertificate.builder()
        .certificateModel(
            CertificateModel.builder().build()
        )
        .build();

    when(certificateRepository.findByCertificatesRequest(SICK_LEAVES_REQUEST))
        .thenReturn(List.of(certificate));

    final var result = getSickLeaveCertificatesDomainService.get(SICK_LEAVES_REQUEST);
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnListOfSickLeaveCertificateBuiltFromSickLeaveProvider() {
    final var expectedSickLeaveCertificate = SickLeaveCertificate.builder().build();
    final var sickLeaveProvider = mock(SickLeaveProvider.class);
    final var certificate = MedicalCertificate.builder()
        .certificateModel(
            CertificateModel.builder()
                .sickLeaveProvider(sickLeaveProvider)
                .build()
        )
        .build();

    when(certificateRepository.findByCertificatesRequest(SICK_LEAVES_REQUEST))
        .thenReturn(List.of(certificate));
    when(sickLeaveProvider.build(certificate, false))
        .thenReturn(Optional.of(expectedSickLeaveCertificate));

    final var result = getSickLeaveCertificatesDomainService.get(SICK_LEAVES_REQUEST);
    assertEquals(List.of(expectedSickLeaveCertificate), result);
  }
}
