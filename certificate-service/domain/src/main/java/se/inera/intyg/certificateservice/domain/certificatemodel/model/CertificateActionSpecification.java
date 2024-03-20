package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;

@Value
@Builder
public class CertificateActionSpecification {

  CertificateActionType certificateActionType;
}
