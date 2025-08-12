package se.inera.intyg.certificateservice.domain.certificatemodel.model;

public enum IcfCodesPropertyType {
  FUNKTIONSNEDSATTNINGAR("disability"),
  AKTIVITETSBEGRANSNINGAR("activityLimitation");

  private final String icfCodePropertyName;

  IcfCodesPropertyType(String icfCodePropertyName) {
    this.icfCodePropertyName = icfCodePropertyName;
  }
}
