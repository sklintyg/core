package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CitizenAvailableFunction {

  CitizenAvailableFunctionType type;
  String name;
  String description;
  String title;
  String body;
  @Builder.Default
  List<CitizenAvailableFunctionInformation> information = Collections.emptyList();
  boolean enabled;
}
