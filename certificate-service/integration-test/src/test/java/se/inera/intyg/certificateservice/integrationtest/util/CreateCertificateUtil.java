package se.inera.intyg.certificateservice.integrationtest.util;

import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;

public class CreateCertificateUtil {

  public static CertificateDTO createCertificate(
      CreateCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return null;
    }

    return response.getCertificate();
  }
}
