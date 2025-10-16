package se.inera.intyg.certificateservice.integrationtest.common.util;

import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;

public class ResourceLinkUtil {

  public static ResourceLinkDTO resourceLink(
      CertificateTypeInfoDTO certificateTypeInfoDTOS, ResourceLinkTypeDTO type) {
    if (certificateTypeInfoDTOS == null) {
      return null;
    }

    return certificateTypeInfoDTOS.getLinks().stream()
        .filter(resourceLinkDTO -> type.equals(resourceLinkDTO.getType()))
        .limit(1)
        .findAny()
        .orElse(null);
  }
}
