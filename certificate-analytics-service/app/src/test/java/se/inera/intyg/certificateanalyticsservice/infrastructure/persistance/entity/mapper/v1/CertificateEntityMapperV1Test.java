package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;

class CertificateEntityMapperV1Test {

  @Test
  void shouldMapCertificateCorrectly() {
    final var expected = CertificateEntity.builder()
        .certificateId(TestDataConstants.CERTIFICATE_ID)
        .certificateType(TestDataConstants.CERTIFICATE_TYPE)
        .certificateTypeVersion(TestDataConstants.CERTIFICATE_TYPE_VERSION)
        .patient(TestDataEntities.patientEntity())
        .unit(TestDataEntities.unitEntity())
        .careProvider(TestDataEntities.careProviderEntity())
        .build();
    final var certificateV1 = se.inera.intyg.certificateanalyticsservice.testdata.TestDataCertificateAnalyticsMessages.certificate();
    final var result = CertificateEntityMapperV1.map(certificateV1);
    assertEquals(expected, result);
  }
}

