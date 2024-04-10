package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReason;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReasonEntity;

public class RevokedReasonEntityMapper {

  private RevokedReasonEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static RevokedReasonEntity toEntity(String reason) {
    final var revokedReason = RevokedReason.valueOf(reason);
    return RevokedReasonEntity.builder()
        .key(revokedReason.getKey())
        .reason(revokedReason.name())
        .build();
  }
}
