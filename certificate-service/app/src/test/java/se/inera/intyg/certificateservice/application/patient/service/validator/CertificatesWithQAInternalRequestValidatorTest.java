package se.inera.intyg.certificateservice.application.patient.service.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesWithQAInternalRequest;

class CertificatesWithQAInternalRequestValidatorTest {

  private static final List<String> CERTIFICATE_IDS = List.of("certificateId1", "certificateId2",
      "certificateId3");
  private CertificatesWithQARequestValidator validator;
  private CertificatesWithQAInternalRequest.CertificatesWithQAInternalRequestBuilder requestBuilder;

  @BeforeEach
  void setUp() {
    validator = new CertificatesWithQARequestValidator();
    requestBuilder = CertificatesWithQAInternalRequest.builder()
        .certificateIds(CERTIFICATE_IDS);
  }

  @Test
  void shallThrowIfCertificateIdsIsNull() {
    final var request = requestBuilder.certificateIds(null).build();
    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request));
    assertEquals("Required parameter missing: certificateIds",
        illegalArgumentException.getMessage());
  }

  @Test
  void shallNotThrowIfCertificateIdsIsNotNull() {
    final var request = requestBuilder.build();
    assertDoesNotThrow(() -> validator.validate(request));
  }
}
