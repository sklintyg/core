package se.inera.intyg.certificateservice.integrationtest.util;

import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateTypeVersionResponse;

public class CertificateModelIdUtil {

  public static CertificateModelIdDTO certificateModelId(
      GetLatestCertificateTypeVersionResponse response) {
    if (response == null || response.getCertificateModelId() == null) {
      return null;
    }

    return response.getCertificateModelId();
  }
}
