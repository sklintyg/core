package se.inera.intyg.certificateservice.domain.diagnosiscode.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Diagnosis {

  DiagnosisCode code;
  DiagnosisDescription description;
}
