package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.Period;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
public class ElementConfigurationDate implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.DATE;
  String id;
  Period min;
  Period max;
}
