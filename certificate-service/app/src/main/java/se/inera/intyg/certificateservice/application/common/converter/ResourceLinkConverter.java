package se.inera.intyg.certificateservice.application.common.converter;

import java.util.Optional;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Component
public class ResourceLinkConverter {

  public ResourceLinkDTO convert(CertificateAction certificateAction,
      Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    return ResourceLinkDTO.builder()
        .type(ResourceLinkTypeDTO.toResourceLinkType(certificateAction.getType()))
        .name(certificateAction.getName(certificate))
        .description(certificateAction.getDescription(certificate))
        .body(certificateAction.getBody(certificate, Optional.of(actionEvaluation)))
        .enabled(certificateAction.isEnabled(certificate, Optional.of(actionEvaluation)))
        .build();
  }
}
