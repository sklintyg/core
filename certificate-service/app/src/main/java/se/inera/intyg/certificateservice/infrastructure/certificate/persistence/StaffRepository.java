package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper.toEntity;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffVersionEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffVersionEntityRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StaffRepository {

  private final StaffEntityRepository staffEntityRepository;
  private final StaffVersionEntityRepository staffVersionEntityRepository;

  @Transactional
  public StaffEntity staff(Staff issuer) {
    return staffEntityRepository.findByHsaId(issuer.hsaId().id())
        .map(staffEntity -> updateStaffVersion(staffEntity, issuer))
        .orElseGet(() -> staffEntityRepository.save(toEntity(issuer)));
  }

  @Transactional
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
        staffs.stream().map(staff -> staff.hsaId().id()).distinct().toList()
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
    var newStaffEntity = StaffEntityMapper.toEntity(staff);
    if (!staffEntity.equals(newStaffEntity)) {
      return saveStaffVersion(staffEntity, newStaffEntity);
    }
    return staffEntity;
  }

  private StaffEntity saveStaffVersion(StaffEntity staffEntity, StaffEntity newStaffEntity) {
    try {
      final var staffVersionEntity = StaffVersionEntityMapper.toEntity(staffEntity);
      staffEntity.updateWith(newStaffEntity);
      var result = staffEntityRepository.save(staffEntity);
      updateStaffVersionHistory(staffVersionEntity);
      return result;
    } catch (OptimisticLockException e) {
      log.info("Skipped updating StaffEntity {} because it was updated concurrently",
          staffEntity.getHsaId());
      return staffEntityRepository.findByHsaId(staffEntity.getHsaId()).orElse(staffEntity);
    }
  }

  private void updateStaffVersionHistory(StaffVersionEntity staffVersionEntity) {
    final var latestVersion =
        staffVersionEntityRepository.findFirstByHsaIdOrderByValidFromDesc(
            staffVersionEntity.getHsaId());

    latestVersion.ifPresent(
        versionEntity -> staffVersionEntity.setValidFrom(versionEntity.getValidTo()));

    staffVersionEntityRepository.save(staffVersionEntity);
  }
}
