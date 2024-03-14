package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffRole;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffRoleEntity;

public class StaffEntityMapper {

  private StaffEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static StaffEntity toEntity(Staff staff) {
    final var staffRole = StaffRole.valueOf(staff.role().name());
    return StaffEntity.builder()
        .hsaId(staff.hsaId().id())
        .firstName(staff.name().firstName())
        .middleName(staff.name().middleName())
        .lastName(staff.name().lastName())
        .role(
            StaffRoleEntity.builder()
                .key(staffRole.getKey())
                .role(staffRole.name())
                .build()
        )
        .build();
  }

  public static Staff toDomain(StaffEntity entity) {
    return Staff.builder()
        .hsaId(new HsaId(entity.getHsaId()))
        .name(
            Name.builder()
                .firstName(entity.getFirstName())
                .middleName(entity.getMiddleName())
                .lastName(entity.getLastName())
                .build()
        )
        .role(
            Role.valueOf(entity.getRole().getRole())
        )
        .build();
  }
}
