package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.service.converter.SickLeaveConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;
import se.inera.intyg.certificateservice.domain.certificate.service.GetSickLeaveCertificateDomainService;

@ExtendWith(MockitoExtension.class)
class GetSickLeaveCertificateInternalServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  @Mock
  GetSickLeaveCertificateDomainService getSickLeaveCertificateDomainService;
  @Mock
  SickLeaveConverter sickLeaveConverter;
  @InjectMocks
  GetSickLeaveCertificateInternalService getSickLeaveCertificateInternalService;

  @Test
  void shallThrowIfCertificateIdIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> getSickLeaveCertificateInternalService.get(null, false));
  }


  @Test
  void shallThrowIfCertificateIdIsBlank() {
    assertThrows(IllegalArgumentException.class,
        () -> getSickLeaveCertificateInternalService.get("", false));
  }

  @Test
  void shallReturnGetSickLeaveCertificateInternalResponseWithAvailableTrue() {
    when(getSickLeaveCertificateDomainService.get(new CertificateId(CERTIFICATE_ID), false))
        .thenReturn(Optional.of(SickLeaveCertificate.builder().build()));

    final var response = getSickLeaveCertificateInternalService.get(
        CERTIFICATE_ID,
        false
    );

    assertTrue(response.isAvailable());
  }

  @Test
  void shallReturnGetSickLeaveCertificateInternalResponseWithSickLeave() {
    final var sickLeaveCertificate = SickLeaveCertificate.builder().build();
    final var expectedSickLeaveDTO = SickLeaveCertificateDTO.builder().build();

    when(getSickLeaveCertificateDomainService.get(new CertificateId(CERTIFICATE_ID), false))
        .thenReturn(Optional.of(sickLeaveCertificate));
    when(sickLeaveConverter.toSickLeaveCertificate(sickLeaveCertificate))
        .thenReturn(expectedSickLeaveDTO);

    final var response = getSickLeaveCertificateInternalService.get(
        CERTIFICATE_ID,
        false
    );

    assertEquals(expectedSickLeaveDTO, response.getSickLeaveCertificate());
  }

  @Test
  void shallReturnGetSickLeaveCertificateInternalResponseWithSickLeaveAndIgnoreMddelRulesIsTrue() {
    final var sickLeaveCertificate = SickLeaveCertificate.builder().build();
    final var expectedSickLeaveDTO = SickLeaveCertificateDTO.builder().build();

    when(getSickLeaveCertificateDomainService.get(new CertificateId(CERTIFICATE_ID), true))
        .thenReturn(Optional.of(sickLeaveCertificate));
    when(sickLeaveConverter.toSickLeaveCertificate(sickLeaveCertificate))
        .thenReturn(expectedSickLeaveDTO);

    final var response = getSickLeaveCertificateInternalService.get(
        CERTIFICATE_ID,
        true
    );

    assertEquals(expectedSickLeaveDTO, response.getSickLeaveCertificate());
  }
}