package se.inera.intyg.certificateanalyticsservice.testdata;

import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CARE_PROVIDER_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE_VERSION;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.MESSAGE_ID_CREATED;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.MESSAGE_ID_SENT;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.MESSAGE_ID_SIGNED;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ORIGIN;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.PATIENT_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ROLE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.SESSION_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.STAFF_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.TIMESTAMP;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.UNIT_ID;

import se.inera.intyg.certificateanalyticsservice.application.messages.model.common.EventType;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventCertificateV1;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventV1;

public class TestDataCertificateAnalyticsMessages {

  private TestDataCertificateAnalyticsMessages() {
    throw new IllegalStateException("Utility class");
  }

  public static CertificateAnalyticsEventMessageV1 createdEventMessage() {
    return CertificateAnalyticsEventMessageV1.builder()
        .messageId(MESSAGE_ID_CREATED)
        .type("certificate.analytics.event")
        .schemaVersion("v1")
        .certificate(certificate())
        .event(event(EventType.CREATED))
        .build();
  }

  public static CertificateAnalyticsEventMessageV1 sentEventMessage() {
    return CertificateAnalyticsEventMessageV1.builder()
        .messageId(MESSAGE_ID_SENT)
        .type("certificate.analytics.event")
        .schemaVersion("v1")
        .certificate(certificate())
        .event(event(EventType.SENT))
        .build();
  }

  public static CertificateAnalyticsEventMessageV1 signedEventMessage() {
    return CertificateAnalyticsEventMessageV1.builder()
        .messageId(MESSAGE_ID_SIGNED)
        .type("certificate.analytics.event")
        .schemaVersion("v1")
        .certificate(certificate())
        .event(event(EventType.SIGNED))
        .build();
  }

  public static CertificateAnalyticsEventCertificateV1 certificate() {
    return CertificateAnalyticsEventCertificateV1.builder()
        .id(CERTIFICATE_ID)
        .type(CERTIFICATE_TYPE)
        .typeVersion(CERTIFICATE_TYPE_VERSION)
        .patientId(PATIENT_ID)
        .unitId(UNIT_ID)
        .careProviderId(CARE_PROVIDER_ID)
        .staffId(STAFF_ID)
        .build();
  }

  public static CertificateAnalyticsEventV1 event(EventType eventType) {
    return CertificateAnalyticsEventV1.builder()
        .timestamp(TIMESTAMP)
        .messageType(eventType.name())
        .staffId(STAFF_ID)
        .role(ROLE)
        .unitId(UNIT_ID)
        .careProviderId(CARE_PROVIDER_ID)
        .origin(ORIGIN)
        .sessionId(SESSION_ID)
        .messageType(eventType.name())
        .build();
  }
}
