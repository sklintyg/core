package se.inera.intyg.certificateservice.domain.unit.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class UnitStatistics {

  private long certificateCount;
  private long messageCount;

  public UnitStatistics(long certificateCount, long messageCount) {
    this.certificateCount = certificateCount;
    this.messageCount = messageCount;
  }
}
