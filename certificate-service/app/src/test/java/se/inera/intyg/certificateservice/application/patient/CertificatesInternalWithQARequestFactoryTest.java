package se.inera.intyg.certificateservice.application.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesInternalWithQARequest;
import se.inera.intyg.certificateservice.application.certificate.service.CertificatesWithQARequestFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;

class CertificatesInternalWithQARequestFactoryTest {

  private static final String CERTIFICATE_IDS_1 = "certificateId1";
  private static final String CERTIFICATE_IDS_2 = "certificateId2";
  private CertificatesInternalWithQARequest.CertificatesInternalWithQARequestBuilder requestBuilder;
  private CertificatesWithQARequestFactory certificatesWithQARequestFactory;

  @BeforeEach
  void setUp() {
    certificatesWithQARequestFactory = new CertificatesWithQARequestFactory();
    requestBuilder = CertificatesInternalWithQARequest.builder()
        .certificateIds(List.of(CERTIFICATE_IDS_1, CERTIFICATE_IDS_2));
  }

  @Test
  void shallIncludeCertificateIds() {
    final var expectedCertificateIds = List.of(new CertificateId(CERTIFICATE_IDS_1),
        new CertificateId(CERTIFICATE_IDS_2));
    final var request = requestBuilder.build();
    assertEquals(expectedCertificateIds,
        certificatesWithQARequestFactory.create(request).certificateIds());
  }

  @Test
  void shallReturnEmptyListOfCertificateIds() {
    final var request = requestBuilder
        .certificateIds(Collections.emptyList())
        .build();
    assertEquals(Collections.emptyList(),
        certificatesWithQARequestFactory.create(request).certificateIds());
  }
}
