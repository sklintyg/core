package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper.toEntity;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;

@Repository
@RequiredArgsConstructor
public class StaffRepository {

  private final StaffEntityRepository staffEntityRepository;

  public StaffEntity staff(Staff issuer) {
    return staffEntityRepository.findByHsaId(issuer.hsaId().id())
        .orElseGet(
            () -> staffEntityRepository.save(
                toEntity(issuer)
            )
        );
  }

  public Map<String, StaffEntity> staffs(Certificate certificate) {
    final var staffs = new ArrayList<Staff>();
    staffs.add(certificate.certificateMetaData().issuer());
    if (certificate.sent() != null && certificate.sent().sentBy() != null) {
      staffs.add(certificate.sent().sentBy());
    }

    final var staffEntities = staffEntityRepository.findStaffEntitiesByHsaIdIn(
        staffs.stream()
            .map(staff -> staff.hsaId().id())
            .distinct()
            .toList()
    );

    final var staffEntityMap = staffEntities.stream()
        .collect(Collectors.toMap(StaffEntity::getHsaId, Function.identity()));

    staffs.forEach(staff -> {
          if (!staffEntityMap.containsKey(staff.hsaId().id())) {
            final var staffEntity = staffEntityRepository.save(toEntity(staff));
            staffEntityMap.put(staffEntity.getHsaId(), staffEntity);
          }
        }
    );

    return staffEntityMap;
  }
}
