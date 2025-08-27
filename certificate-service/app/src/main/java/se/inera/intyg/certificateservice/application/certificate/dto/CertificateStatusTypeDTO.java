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

  public static Status toStatus(CertificateStatusTypeDTO statusTypeDTO) {
    return switch (statusTypeDTO) {
      case UNSIGNED -> Status.DRAFT;
      case LOCKED -> Status.LOCKED_DRAFT;
      case SIGNED -> Status.SIGNED;
      case REVOKED, LOCKED_REVOKED -> Status.REVOKED;
    };
  }
}