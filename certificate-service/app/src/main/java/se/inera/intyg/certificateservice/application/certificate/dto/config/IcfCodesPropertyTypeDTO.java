package se.inera.intyg.certificateservice.application.certificate.dto.config;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.IcfCodesPropertyType;

public enum IcfCodesPropertyTypeDTO {
  FUNKTIONSNEDSATTNINGAR("disability"),
  AKTIVITETSBEGRANSNINGAR("activityLimitation");

  private final String icfCodePropertyName;

  IcfCodesPropertyTypeDTO(String icfCodePropertyName) {
    this.icfCodePropertyName = icfCodePropertyName;
  }

  public static IcfCodesPropertyTypeDTO toIcfCodesPropertyType(
      IcfCodesPropertyType icfCodesPropertyTypes) {
    return switch (icfCodesPropertyTypes) {
      case FUNKTIONSNEDSATTNINGAR -> FUNKTIONSNEDSATTNINGAR;
      case AKTIVITETSBEGRANSNINGAR -> AKTIVITETSBEGRANSNINGAR;
    };
  }

}
