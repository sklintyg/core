package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReason.INCORRECT_PATIENT;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReason.OTHER_SERIOUS_ERROR;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RevokedReasonEntityMapperTest {

  @Nested
  class MapWrongPatient {

    private static final String REASON_WRONG_PATIENT = INCORRECT_PATIENT.name();

    @Test
    void shallMapReasonToRevokedReasonEntity() {
      final var entity = RevokedReasonEntityMapper.toEntity(REASON_WRONG_PATIENT);
      assertEquals(INCORRECT_PATIENT.name(), entity.getReason());
    }

    @Test
    void shallMapKeyToRevokedReasonEntity() {
      final var entity = RevokedReasonEntityMapper.toEntity(REASON_WRONG_PATIENT);
      assertEquals(INCORRECT_PATIENT.getKey(), entity.getKey());
    }
  }


  @Nested
  class MapOtherSeriousError {

    private static final String REASON_OTHER_SERIOUS_ERROR = OTHER_SERIOUS_ERROR.name();

    @Test
    void shallMapReasonToRevokedReasonEntity() {
      final var entity = RevokedReasonEntityMapper.toEntity(REASON_OTHER_SERIOUS_ERROR);
      assertEquals(OTHER_SERIOUS_ERROR.name(), entity.getReason());
    }

    @Test
    void shallMapKeyToRevokedReasonEntity() {
      final var entity = RevokedReasonEntityMapper.toEntity(REASON_OTHER_SERIOUS_ERROR);
      assertEquals(OTHER_SERIOUS_ERROR.getKey(), entity.getKey());
    }
  }
}
