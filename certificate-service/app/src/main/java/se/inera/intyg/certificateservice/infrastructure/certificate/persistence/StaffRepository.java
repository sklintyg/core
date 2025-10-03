package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper.toDomain;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper.toEntity;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffVersionEntityRepository;

@Repository
@RequiredArgsConstructor
public class StaffRepository {

  private final StaffEntityRepository staffEntityRepository;
  private final StaffVersionEntityRepository staffVersionEntityRepository;

  public StaffEntity staff(Staff issuer) {
		return staffEntityRepository.findByHsaId(issuer.hsaId().id())
				.map(staffEntity -> updateStaffVersion(staffEntity, issuer))
				.orElseGet(() -> staffEntityRepository.save(toEntity(issuer)));
	}


  public Map<HsaId, StaffEntity> staffs(Certificate certificate) {
    final var staffs = new ArrayList<Staff>();
    staffs.add(certificate.certificateMetaData().issuer());
    if (certificate.sent() != null && certificate.sent().sentBy() != null) {
      staffs.add(certificate.sent().sentBy());
    }

    if (certificate.revoked() != null && certificate.revoked().revokedBy() != null) {
      staffs.add(certificate.revoked().revokedBy());
    }

    if (certificate.readyForSign() != null && certificate.readyForSign().readyForSignBy() != null) {
      staffs.add(certificate.readyForSign().readyForSignBy());
    }

    final var staffEntities = staffEntityRepository.findStaffEntitiesByHsaIdIn(
        staffs.stream()
            .map(staff -> staff.hsaId().id())
            .distinct()
            .toList()
    );

		final var staffEntityMap = staffEntities.stream()
				.collect(Collectors.toMap(staff -> HsaId.create(staff.getHsaId()), Function.identity()));

		staffs.forEach(staff -> {
			if (!staffEntityMap.containsKey(staff.hsaId())) {
				final var staffEntity = staffEntityRepository.save(toEntity(staff));
				staffEntityMap.put(HsaId.create(staffEntity.getHsaId()), staffEntity);
			} else if (!toEntity(staff).equals(staffEntityMap.get(staff.hsaId()))) {
				final var staffEntity = updateStaffVersion(staffEntityMap.get(staff.hsaId()), staff);
				staffEntityMap.replace(HsaId.create(staffEntity.getHsaId()), staffEntity);
			}
		});

    return staffEntityMap;
  }

	private StaffEntity updateStaffVersion(StaffEntity staffEntity, Staff staff) {
		if (!staff.equals(toDomain(staffEntity))) {
			final var staffVersionEntity = StaffVersionEntityMapper.toEntity(staffEntity);
			final var existingVersions = staffVersionEntityRepository.
					findAllByHsaIdOrderByValidFromDesc(staff.hsaId().id());

			if (!existingVersions.isEmpty() && !existingVersions.contains(staffVersionEntity)) {
				staffVersionEntity.setValidFrom(existingVersions.getFirst().getValidTo());
			}

			var newStaffEntity = StaffEntityMapper.toEntity(staff);

			staffEntity.setFirstName(newStaffEntity.getFirstName());
			staffEntity.setMiddleName(newStaffEntity.getMiddleName());
			staffEntity.setLastName(newStaffEntity.getLastName());
			staffEntity.setRole(newStaffEntity.getRole());
			staffEntity.setPaTitles(newStaffEntity.getPaTitles());
			staffEntity.setSpecialities(newStaffEntity.getSpecialities());
			staffEntity.setHealthcareProfessionalLicences(newStaffEntity.getHealthcareProfessionalLicences());

			staffVersionEntityRepository.save(staffVersionEntity);
			return staffEntityRepository.save(staffEntity);
		}
		return staffEntity;
	}
}
