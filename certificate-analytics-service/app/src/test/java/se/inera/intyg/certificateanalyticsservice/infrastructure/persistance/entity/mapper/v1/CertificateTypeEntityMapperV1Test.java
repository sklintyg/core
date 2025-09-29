package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataCertificateAnalyticsMessages;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;

class CertificateTypeEntityMapperV1Test {

  @Test
  void shouldMapCertificateTypeCorrectly() {
    final var expected = TestDataEntities.certificateTypeEntity();
    final var certificateV1 = TestDataCertificateAnalyticsMessages.certificate();
    final var result = CertificateTypeEntityMapperV1.map(certificateV1);
    assertEquals(expected, result);
  }
}

