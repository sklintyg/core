package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataCertificateAnalyticsMessages;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;

class CertificateEntityMapperV1Test {

  @Test
  void shouldMapCertificateCorrectly() {
    final var expected = TestDataEntities.certificateEntity();

    final var result = CertificateEntityMapperV1.map(
        TestDataCertificateAnalyticsMessages.certificate()
    );

    assertEquals(expected, result);
  }
}
