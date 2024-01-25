package se.inera.intyg.certificateservice.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CertificateAction {

  CertificateActionType certificateActionType;
}
