package se.inera.intyg.certificateservice.application.common.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;

@Component
public class ResourceLinkConverter {

  public ResourceLinkDTO convert(CertificateAction certificateAction) {
    return ResourceLinkDTO.builder()
        .type(ResourceLinkTypeDTO.toResourceLinkType(certificateAction.getType()))
        .name(certificateAction.getName())
        .description(certificateAction.getDescription())
        .body(certificateAction.getBody())
        .enabled(certificateAction.isEnabled())
        .build();
  }
}
