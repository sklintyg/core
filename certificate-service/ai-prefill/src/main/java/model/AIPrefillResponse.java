package model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Value
@Builder
public class AIPrefillResponse {

  Certificate certificate;
  String informationAboutAIGeneration;
}
