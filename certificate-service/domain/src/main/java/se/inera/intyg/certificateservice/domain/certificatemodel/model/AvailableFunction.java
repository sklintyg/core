package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AvailableFunction {

  AvailableFunctionType type;
  String name;
  String description;
  String title;
  String body;
  @Builder.Default
  List<AvailableFunctionInformation> information = Collections.emptyList();
  boolean enabled;
}
