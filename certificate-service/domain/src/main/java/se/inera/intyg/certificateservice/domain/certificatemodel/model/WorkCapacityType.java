package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Getter;

@Getter
public enum WorkCapacityType {
  EN_ATTANDEL("12,5 procent"),
  EN_FJARDEDEL("25 procent"),
  HALVA("50 procent"),
  TRE_FJARDEDELAR("75 procent"),
  HELA("100 procent");

  private final String label;
  public static final String CODE_SYSTEM = "KV_FKMU_0009";

  WorkCapacityType(String label) {
    this.label = label;
  }

}
