package se.inera.intyg.certificateservice.domain.unit.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class UnitStatistics {

  private int certificateCount;
  private int messageCount;

  public UnitStatistics(int certificateCount, int messageCount) {
    this.certificateCount = certificateCount;
    this.messageCount = messageCount;
  }
}
