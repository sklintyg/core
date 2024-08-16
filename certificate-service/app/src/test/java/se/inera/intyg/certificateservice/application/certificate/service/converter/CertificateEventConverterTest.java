package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateEventDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateEventTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

class CertificateEventConverterTest {

  @Test
  void shouldConvertCertificateEvent() {
    final var now = LocalDateTime.now();
    final var expected = CertificateEventDTO.builder()
        .certificateId("C_ID")
        .relatedCertificateId("R_ID")
        .relatedCertificateStatus(CertificateStatusTypeDTO.REVOKED)
        .timestamp(now)
        .type(CertificateEventTypeDTO.COMPLEMENTED)
        .build();

    assertEquals(
        expected,
        new CertificateEventConverter().convert(
            CertificateEvent.builder()
                .certificateId(new CertificateId("C_ID"))
                .relatedCertificateId(new CertificateId("R_ID"))
                .type(CertificateEventType.COMPLEMENTED)
                .timestamp(now)
                .relatedCertificateStatus(Status.REVOKED)
                .build()
        )
    );
  }

  @Test
  void shouldConvertCertificateWithoutRelatedCertificate() {
    final var now = LocalDateTime.now();
    final var expected = CertificateEventDTO.builder()
        .certificateId("C_ID")
        .timestamp(now)
        .type(CertificateEventTypeDTO.COMPLEMENTED)
        .build();

    assertEquals(
        expected,
        new CertificateEventConverter().convert(
            CertificateEvent.builder()
                .certificateId(new CertificateId("C_ID"))
                .type(CertificateEventType.COMPLEMENTED)
                .timestamp(now)
                .build()
        )
    );
  }

}