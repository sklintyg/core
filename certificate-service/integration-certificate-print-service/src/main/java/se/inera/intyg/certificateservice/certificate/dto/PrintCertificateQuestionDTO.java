package se.inera.intyg.certificateservice.certificate.dto;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;

@Value
@Builder
public class PrintCertificateQuestionDTO {

  String id;
  String name;
  String description;

  ElementSimplifiedValue value;
}
