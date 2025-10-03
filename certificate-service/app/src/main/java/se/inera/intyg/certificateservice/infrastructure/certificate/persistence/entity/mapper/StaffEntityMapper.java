package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.util.ArrayList;
import java.util.stream.Collectors;
import se.inera.intyg.certificateservice.domain.common.model.AllowCopy;
import se.inera.intyg.certificateservice.domain.common.model.Blocked;
import se.inera.intyg.certificateservice.domain.common.model.HealthCareProfessionalLicence;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.HealthcareProfessionalLicenceEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PaTitleEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.SpecialityEmbeddable;
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
        .paTitles(
            staff.paTitles().stream()
                .map(
                    paTitle -> PaTitleEmbeddable.builder()
                        .code(paTitle.code())
                        .description(paTitle.description())
                        .build()
                )
                .collect(Collectors.toCollection(ArrayList::new))
        )
        .specialities(
            staff.specialities().stream()
                .map(
                    speciality -> SpecialityEmbeddable.builder()
                        .speciality(speciality.value())
                        .build()
                )
                .collect(Collectors.toCollection(ArrayList::new))
        )
        .healthcareProfessionalLicences(
            staff.healthCareProfessionalLicence().stream()
                .map(
                    healthCareProfessionalLicence -> HealthcareProfessionalLicenceEmbeddable.builder()
                        .healthcareProfessionalLicence(healthCareProfessionalLicence.value())
                        .build()
                )
                .collect(Collectors.toCollection(ArrayList::new))
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
        .paTitles(
            entity.getPaTitles().stream()
                .map(paTitleEmbeddable -> new PaTitle(
                        paTitleEmbeddable.getCode(),
                        paTitleEmbeddable.getDescription()
                    )
                )
                .toList()
        )
        .specialities(
            entity.getSpecialities().stream()
                .map(specialityEmbeddable -> new Speciality(specialityEmbeddable.getSpeciality()))
                .toList()
        )
        .healthCareProfessionalLicence(
            entity.getHealthcareProfessionalLicences().stream()
                .map(healthcareProfessionalLicenceEmbeddable -> new HealthCareProfessionalLicence(
                        healthcareProfessionalLicenceEmbeddable.getHealthcareProfessionalLicence()
                    )
                )
                .toList()
        )
        .allowCopy(new AllowCopy(true))
        .blocked(new Blocked(false))
        .build();
  }

	public static boolean hasChanges(StaffEntity entity, Staff staff) {
		if (!entity.getFirstName().equals(staff.name().firstName())) return true;
		if (!entity.getMiddleName().equals(staff.name().middleName())) return true;
		if (!entity.getLastName().equals(staff.name().lastName())) return true;
		if (!entity.getRole().getRole().equals(staff.role().name())) return true;

		// Compare lists (simplest way: compare sizes and content)
		if (!entity.getPaTitles().stream()
				.map(PaTitleEmbeddable::getCode)
				.toList()
				.equals(staff.paTitles().stream().map(PaTitle::code).toList())) return true;

		if (!entity.getSpecialities().stream()
				.map(SpecialityEmbeddable::getSpeciality)
				.toList()
				.equals(staff.specialities().stream().map(Speciality::value).toList())) return true;

		if (!entity.getHealthcareProfessionalLicences().stream()
				.map(HealthcareProfessionalLicenceEmbeddable::getHealthcareProfessionalLicence)
				.toList()
				.equals(staff.healthCareProfessionalLicence().stream().map(HealthCareProfessionalLicence::value).toList())) return true;

		return false;
	}

}
