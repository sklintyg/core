package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE_VERSION;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateTypeEntity;

class CertificateTypeEntityMapperTest {

  @Test
  void shouldMapCertificateTypeCorrectly() {
    final var expected = CertificateTypeEntity.builder()
        .certificateType(CERTIFICATE_TYPE)
        .certificateTypeVersion(CERTIFICATE_TYPE_VERSION)
        .build();
    final var result = CertificateTypeEntityMapper.map(CERTIFICATE_TYPE,
        CERTIFICATE_TYPE_VERSION);
    assertEquals(expected, result);
  }
}
