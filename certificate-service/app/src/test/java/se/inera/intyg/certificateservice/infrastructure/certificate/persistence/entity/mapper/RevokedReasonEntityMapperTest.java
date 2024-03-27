package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReason.ANNAT_ALLVARLIGT_FEL;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReason.FEL_PATIENT;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RevokedReasonEntityMapperTest {

  @Nested
  class MapWrongPatient {

    private static final String REASON_WRONG_PATIENT = "FEL_PATIENT";

    @Test
    void shallMapReasonToRevokedReasonEntity() {
      final var entity = RevokedReasonEntityMapper.toEntity(REASON_WRONG_PATIENT);
      assertEquals(FEL_PATIENT.name(), entity.getReason());
    }

    @Test
    void shallMapKeyToRevokedReasonEntity() {
      final var entity = RevokedReasonEntityMapper.toEntity(REASON_WRONG_PATIENT);
      assertEquals(FEL_PATIENT.getKey(), entity.getKey());
    }
  }


  @Nested
  class MapOtherSeriousError {

    private static final String REASON_OTHER_SERIOUS_ERROR = "ANNAT_ALLVARLIGT_FEL";

    @Test
    void shallMapReasonToRevokedReasonEntity() {
      final var entity = RevokedReasonEntityMapper.toEntity(REASON_OTHER_SERIOUS_ERROR);
      assertEquals(ANNAT_ALLVARLIGT_FEL.name(), entity.getReason());
    }

    @Test
    void shallMapKeyToRevokedReasonEntity() {
      final var entity = RevokedReasonEntityMapper.toEntity(REASON_OTHER_SERIOUS_ERROR);
      assertEquals(ANNAT_ALLVARLIGT_FEL.getKey(), entity.getKey());
    }
  }
}
