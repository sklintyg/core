package se.inera.intyg.certificateservice.integrationtest.common.util;

import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoResponse;

public class CertificateTypeInfoUtil {

  private CertificateTypeInfoUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static CertificateTypeInfoDTO certificateTypeInfo(
      GetCertificateTypeInfoResponse response, String type) {
    if (response == null || response.getList() == null) {
      return null;
    }

    return response.getList().stream()
        .filter(certificateTypeInfoDTO ->
            type.equalsIgnoreCase(certificateTypeInfoDTO.getType())
        )
        .limit(1)
        .findAny()
        .orElse(null);
  }
}
