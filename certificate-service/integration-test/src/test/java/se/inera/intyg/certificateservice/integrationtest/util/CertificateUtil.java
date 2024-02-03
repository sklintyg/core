package se.inera.intyg.certificateservice.integrationtest.util;

import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;

public class CertificateUtil {

  public static String certificateId(CreateCertificateResponse response) {
    final var certificate = certificate(response);
    if (certificate == null || certificate.getMetadata() == null) {
      return null;
    }
    return certificate.getMetadata().getId();
  }

  public static CertificateDTO certificate(CreateCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return null;
    }

    return response.getCertificate();
  }

  public static boolean exists(CertificateExistsResponse response) {
    if (response == null) {
      return false;
    }

    return response.isExists();
  }
}
