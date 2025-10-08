package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitVersionEntity;

public class UnitVersionEntityMapper {

  private UnitVersionEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static UnitVersionEntity toEntity(UnitEntity unitEntity) {
    return UnitVersionEntity.builder()
        .hsaId(unitEntity.getHsaId())
        .name(unitEntity.getName())
        .address(unitEntity.getAddress())
        .zipCode(unitEntity.getZipCode())
        .city(unitEntity.getCity())
        .phoneNumber(unitEntity.getPhoneNumber())
        .email(unitEntity.getEmail())
        .workplaceCode(unitEntity.getWorkplaceCode())
        .type(unitEntity.getType())
        .validTo(LocalDateTime.now())
        .validFrom(null)
        .build();
  }
}
