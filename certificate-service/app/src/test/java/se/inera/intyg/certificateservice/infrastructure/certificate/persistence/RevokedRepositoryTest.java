package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.mockito.Mockito.doReturn;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReason;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReasonEntity;

@ExtendWith(MockitoExtension.class)
class RevokedRepositoryTest {

  @Mock
  private RevokedReasonRepository revokedReasonRepository;
  @InjectMocks
  private RevokedRepository revokedRepository;

  private static final RevokedReasonEntity REVOKED_REASON_ENTITY = RevokedReasonEntity.builder()
      .key(RevokedReason.FEL_PATIENT.getKey())
      .reason(RevokedReason.FEL_PATIENT.name())
      .build();

  @Test
  void shallReturnEntityFromRepository() {
    doReturn(Optional.of(REVOKED_REASON_ENTITY))
        .when(revokedReasonRepository).getByReason(RevokedReason.FEL_PATIENT.name());
    assertEquals(REVOKED_REASON_ENTITY,
        revokedRepository.revokedReason(RevokedReason.FEL_PATIENT.name())
    );
  }
}
