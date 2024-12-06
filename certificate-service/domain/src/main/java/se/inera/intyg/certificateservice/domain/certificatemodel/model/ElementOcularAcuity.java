package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementOcularAcuity {

  String label;
  String withCorrectionId;
  String withoutCorrectionId;
}