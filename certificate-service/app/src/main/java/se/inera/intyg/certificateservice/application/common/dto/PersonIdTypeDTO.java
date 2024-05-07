package se.inera.intyg.certificateservice.application.common.dto;

import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;

public enum PersonIdTypeDTO {

  PERSONAL_IDENTITY_NUMBER,
  COORDINATION_NUMBER;

  public PersonIdType toPersonIdType() {
    return switch (this) {
      case PERSONAL_IDENTITY_NUMBER -> PersonIdType.PERSONAL_IDENTITY_NUMBER;
      case COORDINATION_NUMBER -> PersonIdType.COORDINATION_NUMBER;
    };
  }

  public static PersonIdTypeDTO toPersonIdTypeDTO(PersonIdType personIdType) {
    return switch (personIdType) {
      case PERSONAL_IDENTITY_NUMBER -> PERSONAL_IDENTITY_NUMBER;
      case COORDINATION_NUMBER -> COORDINATION_NUMBER;
    };
  }
}
