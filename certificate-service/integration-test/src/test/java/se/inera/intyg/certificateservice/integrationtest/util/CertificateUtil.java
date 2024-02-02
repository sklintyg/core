package se.inera.intyg.certificateservice.integrationtest.util;

import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;

public class CertificateUtil {

  public static CertificateDTO certificate(
      CreateCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return null;
    }

    return response.getCertificate();
  }
}
