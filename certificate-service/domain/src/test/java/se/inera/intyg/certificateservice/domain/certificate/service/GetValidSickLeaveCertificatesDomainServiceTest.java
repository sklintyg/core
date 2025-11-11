package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SickLeaveProvider;

@ExtendWith(MockitoExtension.class)
class GetValidSickLeaveCertificatesDomainServiceTest {

  private static final List<CertificateId> CERTIFICATE_ID = List.of(
      new CertificateId("CERTIFICATE_ID"));
  @Mock
  CertificateRepository certificateRepository;
  @InjectMocks
  GetValidSickLeaveCertificatesDomainService getValidSickLeaveCertificatesDomainService;

  @Test
  void shouldReturnListOfCertificates() {
    final var medicalCertificate = mock(MedicalCertificate.class);
    final var certificateModel = mock(CertificateModel.class);
    final var sickLeaveProvider = mock(SickLeaveProvider.class);

    when(certificateRepository.findValidSickLeavesByIds(CERTIFICATE_ID)).thenReturn(
        List.of(medicalCertificate));
    when(medicalCertificate.certificateModel()).thenReturn(certificateModel);
    when(certificateModel.sickLeaveProvider()).thenReturn(sickLeaveProvider);

    final var certificates = getValidSickLeaveCertificatesDomainService.get(CERTIFICATE_ID);
    assertEquals(List.of(medicalCertificate), certificates);
  }

  @Test
  void shouldFilterListOfCertificatesSoThatItOnlyIncludesCertificatesWithSickLeaveProvider() {
    final var medicalCertificate = mock(MedicalCertificate.class);
    final var certificateModel = mock(CertificateModel.class);

    when(certificateRepository.findValidSickLeavesByIds(CERTIFICATE_ID)).thenReturn(
        List.of(medicalCertificate));
    when(medicalCertificate.certificateModel()).thenReturn(certificateModel);
    when(certificateModel.sickLeaveProvider()).thenReturn(null);

    final var certificates = getValidSickLeaveCertificatesDomainService.get(CERTIFICATE_ID);
    assertTrue(certificates.isEmpty());
  }
}