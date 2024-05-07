package se.inera.intyg.certificateservice.application.citizen.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Component
public class CitizenCertificateConverter {

  public CertificateDTO convert(Certificate certificate) {
    return CertificateDTO.builder()
        .data(certificate.elementData())
        .metadata()
        .build();
  }

}
