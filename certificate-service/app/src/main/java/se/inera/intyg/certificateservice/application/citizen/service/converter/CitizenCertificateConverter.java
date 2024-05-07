package se.inera.intyg.certificateservice.application.citizen.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Component
public class CitizenCertificateConverter {

  public CitizenCertificateDTO convert(Certificate certificate) {
    return CitizenCertificateDTO.builder().build();

    final var x = CertificateDTO
  }

}
