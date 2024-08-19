package se.inera.intyg.certificateservice.domain.unit.model;

import lombok.Value;
import lombok.With;

@Value
public class UnitStatistics {

  int certificateCount;
  @With
  int messageCount;
}
