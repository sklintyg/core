package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CitizenAvailableFunctionInformation {

  ElementId id;
  String text;
  CitizenAvailableFunctionInformationType type;
}
