package se.inera.intyg.certificateservice.application.common.dto;

import se.inera.intyg.certificateservice.domain.common.model.AccessScope;

public enum AccessScopeTypeDTO {
  WITHIN_CARE_UNIT,
  WITHIN_CARE_PROVIDER,
  ALL_CARE_PROVIDERS;

  public AccessScope toDomain() {
    return switch (this) {
      case WITHIN_CARE_UNIT -> AccessScope.WITHIN_CARE_UNIT;
      case WITHIN_CARE_PROVIDER -> AccessScope.WITHIN_CARE_PROVIDER;
      case ALL_CARE_PROVIDERS -> AccessScope.ALL_CARE_PROVIDERS;
    };
  }
}