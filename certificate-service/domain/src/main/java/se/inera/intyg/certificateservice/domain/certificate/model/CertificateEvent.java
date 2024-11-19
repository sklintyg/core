package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class CertificateEvent {

  CertificateId certificateId;
  LocalDateTime timestamp;
  CertificateEventType type;
  CertificateId relatedCertificateId;
  Status relatedCertificateStatus;
}
