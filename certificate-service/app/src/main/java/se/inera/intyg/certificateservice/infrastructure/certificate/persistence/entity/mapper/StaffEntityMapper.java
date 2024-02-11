package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;

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
