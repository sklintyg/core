package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;

public class StaffEntityMapper {

  public static StaffEntity toEntity(Staff staff) {
    return StaffEntity.builder()
        .hsaId(staff.hsaId().id())
        .name(staff.name().fullName())
        .build();
  }

  public static Staff toDomain(StaffEntity entity) {
    return Staff.builder()
        .hsaId(new HsaId(entity.getHsaId()))
        .build();
  }
}
