package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.PlaceholderCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@ExtendWith(SpringExtension.class)
class RevokePlaceholderCertificateInternalServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  @Mock
  CertificateRepository certificateRepository;
  @InjectMocks
  RevokePlaceholderCertificateInternalService revokePlaceholderCertificateInternalService;


  @Test
  void shouldThrowIfCertificateIdIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> revokePlaceholderCertificateInternalService.revoke(null));
  }

  @Test
  void shouldThrowIfCertificateIdIsBlank() {
    assertThrows(IllegalArgumentException.class,
        () -> revokePlaceholderCertificateInternalService.revoke(""));
  }

  @Test
  void shouldRevokeCertificate() {
    final var placeholderCertificate = mock(PlaceholderCertificate.class);

    when(certificateRepository.getPlaceholderById(new CertificateId(CERTIFICATE_ID))).thenReturn(
        placeholderCertificate);

    revokePlaceholderCertificateInternalService.revoke(CERTIFICATE_ID);

    verify(placeholderCertificate).revoke(null, null);
  }

  @Test
  void shouldPersistRevokedCertificate() {
    final var placeholderCertificate = mock(PlaceholderCertificate.class);

    when(certificateRepository.getPlaceholderById(new CertificateId(CERTIFICATE_ID))).thenReturn(
        placeholderCertificate);

    revokePlaceholderCertificateInternalService.revoke(CERTIFICATE_ID);

    verify(certificateRepository).save(placeholderCertificate);
  }

  @Test
  void shouldReturnCertificate() {
    final var expectedCertificate = mock(PlaceholderCertificate.class);

    when(certificateRepository.getPlaceholderById(new CertificateId(CERTIFICATE_ID))).thenReturn(
        expectedCertificate);
    when(certificateRepository.save(expectedCertificate)).thenReturn(expectedCertificate);

    final var actualCertificate = revokePlaceholderCertificateInternalService.revoke(
        CERTIFICATE_ID);
    assertEquals(expectedCertificate, actualCertificate);
  }
}