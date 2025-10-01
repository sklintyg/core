package se.inera.intyg.certificateanalyticsservice.testdata;

import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CARE_PROVIDER_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE_VERSION;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.EVENT_TIMESTAMP;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.EVENT_TYPE_DRAFT_CREATED;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.MESSAGE_ID_CREATED;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ORIGIN;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.PATIENT_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ROLE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.SCHEMA_VERSION;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.SESSION_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.STAFF_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.TYPE_ANALYTICS_EVENT;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.UNIT_ID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventCertificateV1;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventCertificateV1.CertificateAnalyticsEventCertificateV1Builder;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1.CertificateAnalyticsEventMessageV1Builder;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventV1;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventV1.CertificateAnalyticsEventV1Builder;

public class TestDataMessages {

  private static final ObjectMapper OBJECT_MAPPER = build();

  private TestDataMessages() {
    throw new IllegalStateException("Utility class");
  }

  private static ObjectMapper build() {
    final var om = new ObjectMapper();
    om.registerModule(new JavaTimeModule());
    om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return om;
  }

  public static String toJson(CertificateAnalyticsEventMessageV1 message) {
    try {
      return OBJECT_MAPPER.writeValueAsString(message);
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static CertificateAnalyticsEventMessageV1Builder draftMessageBuilder() {
    return CertificateAnalyticsEventMessageV1.builder()
        .messageId(MESSAGE_ID_CREATED)
        .type(TYPE_ANALYTICS_EVENT)
        .schemaVersion(SCHEMA_VERSION)
        .certificate(
            certificateBuilder()
                .id(CERTIFICATE_ID)
                .unitId(UNIT_ID)
                .careProviderId(CARE_PROVIDER_ID)
                .patientId(PATIENT_ID)
                .type(CERTIFICATE_TYPE)
                .typeVersion(CERTIFICATE_TYPE_VERSION)
                .build()
        )
        .event(
            eventBuilder()
                .messageType(EVENT_TYPE_DRAFT_CREATED)
                .timestamp(LocalDateTime.parse(EVENT_TIMESTAMP))
                .userId(STAFF_ID)
                .role(ROLE)
                .unitId(UNIT_ID)
                .careProviderId(CARE_PROVIDER_ID)
                .sessionId(SESSION_ID)
                .origin(ORIGIN)
                .build()
        );
  }

  private static CertificateAnalyticsEventCertificateV1Builder certificateBuilder() {
    return CertificateAnalyticsEventCertificateV1.builder()
        .id(CERTIFICATE_ID)
        .unitId(UNIT_ID)
        .careProviderId(CARE_PROVIDER_ID)
        .patientId(PATIENT_ID)
        .type(CERTIFICATE_TYPE)
        .typeVersion(CERTIFICATE_TYPE_VERSION);
  }

  private static CertificateAnalyticsEventV1Builder eventBuilder() {
    return CertificateAnalyticsEventV1.builder()
        .timestamp(LocalDateTime.parse(EVENT_TIMESTAMP))
        .userId(STAFF_ID)
        .role(ROLE)
        .unitId(UNIT_ID)
        .careProviderId(CARE_PROVIDER_ID)
        .sessionId(SESSION_ID)
        .origin(ORIGIN);
  }
}
