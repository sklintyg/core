package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Getter;

@Getter
public enum WorkCapacityType {
  EN_ATTONDEL("12,5 procent", "En åttondel av den ordinarie tiden"),
  EN_FJARDEDEL("25 procent", "En fjärdedel av den ordinarie tiden"),
  HALVA("50 procent", "Halva den ordinarie tiden"),
  TRE_FJARDEDELAR("75 procent", "Tre fjärdedelar av den ordinarie tiden"),
  HELA("100 procent", "Hela den ordinarie tiden");

  private final String label;
  private final String displayName;
  public static final String CODE_SYSTEM = "KV_FKMU_0009";

  WorkCapacityType(String label, String displayName) {
    this.label = label;
    this.displayName = displayName;
  }
}
