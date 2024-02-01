package se.inera.intyg.certificateservice.application.common;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;

@Component
public class ResourceLinkConverter {

  public ResourceLinkDTO convert(CertificateAction certificateAction) {
    return ResourceLinkDTO.builder()
        .type(convertType(certificateAction.getType()))
        .name(certificateAction.getName())
        .description(certificateAction.getDescription())
        .enabled(certificateAction.isEnabled())
        .build();
  }

  private ResourceLinkTypeDTO convertType(CertificateActionType type) {
    return switch (type) {
      case CREATE -> ResourceLinkTypeDTO.CREATE_CERTIFICATE;
      case READ -> ResourceLinkTypeDTO.READ_CERTIFICATE;
    };
  }
}
