package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.model.CertificateAction;
import se.inera.intyg.certificateservice.model.CertificateActionType;

@Component
public class ResourceLinkConverter {

  public ResourceLinkDTO convert(CertificateAction certificateAction) {
    return ResourceLinkDTO.builder()
        .type(convertType(certificateAction.getType()))
        .name(certificateAction.getName())
        .description(certificateAction.getDescription())
        .enabled(true)
        .build();
  }

  private ResourceLinkTypeDTO convertType(CertificateActionType type) {
    return switch (type) {
      case CREATE -> ResourceLinkTypeDTO.CREATE_CERTIFICATE;
    };
  }
}
