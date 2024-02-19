package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;

public class StaffEntityMapper {

  private StaffEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static StaffEntity toEntity(Staff staff) {
    return StaffEntity.builder()
        .hsaId(staff.hsaId().id())
        .firstName(staff.name().firstName())
        .middleName(staff.name().middleName())
        .lastName(staff.name().lastName())
        .build();
  }

  public static Staff toDomain(StaffEntity entity) {
    return Staff.builder()
        .hsaId(new HsaId(entity.getHsaId()))
        .name(Name.builder()
            .firstName(entity.getFirstName())
            .middleName(entity.getMiddleName())
            .lastName(entity.getLastName())
            .build())
        .build();
  }
}
