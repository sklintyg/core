package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateEventDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateEventTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEvent;

@Component
public class CertificateEventConverter {
  
  public CertificateEventDTO convert(CertificateEvent event) {
    return CertificateEventDTO.builder()
        .certificateId(event.certificateId().id())
        .relatedCertificateId(
            event.relatedCertificateId() != null ? event.relatedCertificateId().id() : null)
        .relatedCertificateStatus(
            event.relatedCertificateStatus() != null ? CertificateStatusTypeDTO.toType(
                event.relatedCertificateStatus()) : null)
        .timestamp(event.timestamp())
        .type(CertificateEventTypeDTO.valueOf(event.type().name()))
        .build();
  }
}
