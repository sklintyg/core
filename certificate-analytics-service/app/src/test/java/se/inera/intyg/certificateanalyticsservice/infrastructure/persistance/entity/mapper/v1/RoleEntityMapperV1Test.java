package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.common.RoleType;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RoleEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

class RoleEntityMapperV1Test {

  @Test
  void shouldMapRoleCorrectly() {
    final var expected = RoleEntity.builder()
        .role(RoleType.DOCTOR.name())
        .build();
    final var result = RoleEntityMapperV1.map(TestDataConstants.ROLE);
    assertEquals(expected, result);
  }
}

