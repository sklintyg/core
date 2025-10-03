package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import java.util.List;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.HealthcareProfessionalLicenceVersionEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PaTitleVersionEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.SpecialityVersionEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffVersionEntity;

public class StaffVersionEntityMapper {

    private StaffVersionEntityMapper() {
        throw new IllegalStateException("Utility class");
    }
    
    
    public static StaffVersionEntity toEntity(StaffEntity staffEntity) {
        return StaffVersionEntity.builder()
            .hsaId(staffEntity.getHsaId())
            .firstName(staffEntity.getFirstName())
            .middleName(staffEntity.getMiddleName())
            .lastName(staffEntity.getLastName())
            .validFrom(null)
            .validTo(LocalDateTime.now())
            .role(staffEntity.getRole())
            .staff(staffEntity)
            .paTitles(copyPaTitles(staffEntity))
            .specialities(copySpecialities(staffEntity))
            .healthcareProfessionalLicences(copyLicences(staffEntity))
            .build();
    }

    private static List<PaTitleVersionEmbeddable> copyPaTitles(StaffEntity existing) {
        if (existing.getPaTitles() == null) return List.of();
        return existing.getPaTitles().stream()
            .map(pt -> new PaTitleVersionEmbeddable(pt.getCode(), pt.getDescription()))
            .toList();
    }

    private static List<SpecialityVersionEmbeddable> copySpecialities(StaffEntity existing) {
        if (existing.getSpecialities() == null) return List.of();
        return existing.getSpecialities().stream()
            .map(sp -> new SpecialityVersionEmbeddable(sp.getSpeciality()))
            .toList();
    }

    private static List<HealthcareProfessionalLicenceVersionEmbeddable> copyLicences(StaffEntity existing) {
        if (existing.getHealthcareProfessionalLicences() == null) return List.of();
        return existing.getHealthcareProfessionalLicences().stream()
            .map(l -> new HealthcareProfessionalLicenceVersionEmbeddable(l.getHealthcareProfessionalLicence()))
            .toList();
    }
}
