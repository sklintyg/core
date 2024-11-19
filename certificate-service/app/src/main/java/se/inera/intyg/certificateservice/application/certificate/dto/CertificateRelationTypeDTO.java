package se.inera.intyg.certificateservice.application.certificate.dto;

import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;

public enum CertificateRelationTypeDTO {
  REPLACED, COPIED, EXTENDED, COMPLEMENTED;

  public static CertificateRelationTypeDTO toType(RelationType type) {
    return switch (type) {
      case REPLACE -> REPLACED;
      case RENEW -> EXTENDED;
      case COMPLEMENT -> COMPLEMENTED;
    };
  }
}
