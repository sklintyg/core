package se.inera.intyg.certificateservice.application.citizen.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.citizen.validation.CitizenCertificateRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@ExtendWith(MockitoExtension.class)
class CitizenCertificateExistsServiceTest {

  private static final String CERTIFICATE_ID = "CERTIFICATE_ID";

  @Mock
  CertificateRepository certificateRepository;

  @Mock
  CitizenCertificateRequestValidator citizenCertificateRequestValidator;

  @InjectMocks
  CitizenCertificateExistsService citizenCertificateExistsService;

  @Test
  void shouldValidateCertificateId() {
    citizenCertificateExistsService.exist(CERTIFICATE_ID);

    verify(citizenCertificateRequestValidator).validate(CERTIFICATE_ID);
  }

  @Test
  void shouldReturnTrueIfExistsInRepo() {
    when(certificateRepository.exists(new CertificateId(CERTIFICATE_ID)))
        .thenReturn(true);

    final var result = citizenCertificateExistsService.exist(CERTIFICATE_ID);

    assertTrue(result.isExists());
  }

  @Test
  void shouldReturnFalseIfNotExistsInRepo() {
    final var result = citizenCertificateExistsService.exist(CERTIFICATE_ID);

    assertFalse(result.isExists());
  }
}