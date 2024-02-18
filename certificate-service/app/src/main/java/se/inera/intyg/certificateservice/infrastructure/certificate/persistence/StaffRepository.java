package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper.toEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
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
}
