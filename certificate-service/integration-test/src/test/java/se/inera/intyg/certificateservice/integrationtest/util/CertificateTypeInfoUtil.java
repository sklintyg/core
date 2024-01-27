package se.inera.intyg.certificateservice.integrationtest.util;

import java.util.List;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;

public class CertificateTypeInfoUtil {

  public static CertificateTypeInfoDTO certificateTypeInfo(
      List<CertificateTypeInfoDTO> certificateTypeInfoDTOS, String type) {
    if (certificateTypeInfoDTOS == null) {
      return null;
    }
    
    return certificateTypeInfoDTOS.stream()
        .filter(certificateTypeInfoDTO ->
            type.equalsIgnoreCase(certificateTypeInfoDTO.getType())
        )
        .limit(1)
        .findAny()
        .orElse(null);
  }
}
