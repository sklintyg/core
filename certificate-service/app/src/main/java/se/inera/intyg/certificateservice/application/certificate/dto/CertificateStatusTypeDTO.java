package se.inera.intyg.certificateservice.application.certificate.dto;

import se.inera.intyg.certificateservice.domain.certificate.model.Status;

public enum CertificateStatusTypeDTO {
  UNSIGNED,
  LOCKED,
  SIGNED,
  REVOKED,
  LOCKED_REVOKED;

  public static CertificateStatusTypeDTO toCertificateStatusTypeDTO(Status status) {
    return switch (status) {
      case DRAFT -> UNSIGNED;
      case REVOKED, DELETED_DRAFT -> REVOKED;
      case SIGNED -> SIGNED;
      case LOCKED_DRAFT -> LOCKED;
    };
  }
}
