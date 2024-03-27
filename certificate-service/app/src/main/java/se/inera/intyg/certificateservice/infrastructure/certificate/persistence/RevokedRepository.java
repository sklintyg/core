package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;


import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.RevokedReasonEntityMapper.toEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReasonEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.RevokedReasonRepository;

@Repository
@RequiredArgsConstructor
public class RevokedRepository {

  private final RevokedReasonRepository revokedReasonRepository;

  public RevokedReasonEntity revokedReason(String revokedReason) {
    return revokedReasonRepository.findByReason(revokedReason)
        .orElseGet(
            () -> revokedReasonRepository.save(
                toEntity(revokedReason)
            )
        );
  }
}
