package se.inera.intyg.certificateservice.application.certificate.dto;

import se.inera.intyg.certificateservice.domain.certificate.model.Status;

public enum CertificateStatusTypeDTO {
  UNSIGNED,
  LOCKED,
  SIGNED,
  REVOKED,
  LOCKED_REVOKED;

  public static CertificateStatusTypeDTO toType(Status status) {
    return switch (status) {
      case DRAFT -> UNSIGNED;
      case REVOKED -> REVOKED;
      case SIGNED -> SIGNED;
      case LOCKED_DRAFT -> LOCKED;
      case DELETED_DRAFT ->
          throw new IllegalArgumentException("Not able to convert status '%s'".formatted(status)
          );
    };
  }
}
