package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateItemDTO;
import se.inera.intyg.certificateservice.application.certificate.service.converter.SickLeaveConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;
import se.inera.intyg.certificateservice.domain.certificate.service.GetSickLeaveCertificatesDomainService;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;

@ExtendWith(MockitoExtension.class)
class GetSickLeaveCertificatesInternalServiceTest {

  private static final GetSickLeaveCertificatesInternalRequest REQUEST = GetSickLeaveCertificatesInternalRequest.builder()
      .personId(PersonIdDTO.builder()
          .type("PERSONAL_IDENTITY_NUMBER")
          .id("191212121212")
          .build()
      )
      .signedFrom(LocalDate.now().minusDays(30))
      .signedTo(LocalDate.now())
      .certificateTypes(List.of("fk7263"))
      .build();
  @Mock
  GetSickLeaveCertificatesDomainService getSickLeaveCertificatesDomainService;
  @Mock
  SickLeaveConverter sickLeaveConverter;
  @InjectMocks
  GetSickLeaveCertificatesInternalService getSickLeaveCertificatesInternalService;

  @Test
  void shallThrowIfPersonIdIsNull() {
    final var requestWithNullPersonId = GetSickLeaveCertificatesInternalRequest.builder()
        .personId(PersonIdDTO.builder().id(null).build())
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> getSickLeaveCertificatesInternalService.get(requestWithNullPersonId));
  }

  @Test
  void shallThrowIfPersonIdIsBlank() {
    final var requestWithBlankPersonId = GetSickLeaveCertificatesInternalRequest.builder()
        .personId(PersonIdDTO.builder().id("").build())
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> getSickLeaveCertificatesInternalService.get(requestWithBlankPersonId));
  }

  @Test
  void shallReturnGetSickLeaveCertificatesInternalResponseWithCertificates() {
    final var sickLeaveCertificate = SickLeaveCertificate.builder().build();
    final var expectedSickLeaveDTO = SickLeaveCertificateItemDTO.builder().build();

    when(getSickLeaveCertificatesDomainService.get(
        any(CertificatesRequest.class)))
        .thenReturn(List.of(sickLeaveCertificate));
    when(sickLeaveConverter.toSickLeaveCertificateItem(sickLeaveCertificate))
        .thenReturn(expectedSickLeaveDTO);

    final var response = getSickLeaveCertificatesInternalService.get(REQUEST);

    assertEquals(List.of(expectedSickLeaveDTO), response.getCertificates());
  }
}
