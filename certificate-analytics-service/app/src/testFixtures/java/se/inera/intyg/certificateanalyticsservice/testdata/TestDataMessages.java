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
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.STAFF_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.TIMESTAMP;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.UNIT_ID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.UUID;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventCertificateV1;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventCertificateV1.CertificateAnalyticsEventCertificateV1Builder;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1.CertificateAnalyticsEventMessageV1Builder;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventV1;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventV1.CertificateAnalyticsEventV1Builder;

public class TestDataMessages {

  private static final ObjectMapper OBJECT_MAPPER = build();

  private TestDataMessages() {
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
      throw new RuntimeException(e);
    }
  }

  public static final CertificateAnalyticsEventMessageV1 CREATED_EVENT_MESSAGE = draftMessageBuilder()
      .messageId(MESSAGE_ID_CREATED)
      .event(eventBuilder().messageType("CREATED").timestamp(TIMESTAMP).build())
      .certificate(certificateBuilder().id(CERTIFICATE_ID).build())
      .build();

  public static final CertificateAnalyticsEventMessageV1 SENT_EVENT_MESSAGE = draftMessageBuilder()
      .messageId(MESSAGE_ID_SENT)
      .event(eventBuilder().messageType("SENT").timestamp(TIMESTAMP).build())
      .certificate(certificateBuilder().id(CERTIFICATE_ID).build())
      .build();

  public static final CertificateAnalyticsEventMessageV1 SIGNED_EVENT_MESSAGE = draftMessageBuilder()
      .messageId(MESSAGE_ID_SIGNED)
      .event(eventBuilder().messageType("SIGNED").timestamp(TIMESTAMP).build())
      .certificate(certificateBuilder().id(CERTIFICATE_ID).build())
      .build();

  public static CertificateAnalyticsEventCertificateV1 certificate() {
    return CertificateAnalyticsEventCertificateV1.builder()
        .id(CERTIFICATE_ID)
        .type(CERTIFICATE_TYPE)
        .typeVersion(CERTIFICATE_TYPE_VERSION)
        .patientId(PATIENT_ID)
        .unitId(UNIT_ID)
        .careProviderId(CARE_PROVIDER_ID)
        .build();
  }

  public static CertificateAnalyticsEventMessageV1Builder draftMessageBuilder() {
    return CertificateAnalyticsEventMessageV1.builder()
        .messageId(UUID.randomUUID().toString())
        .type("certificate.analytics.event")
        .schemaVersion("v1")
        .certificate(
            certificateBuilder()
                .build()
        )
        .event(
            eventBuilder()
                .messageType("DRAFT_CREATED")
                .build()
        );
  }

  private static CertificateAnalyticsEventCertificateV1Builder certificateBuilder() {
    return CertificateAnalyticsEventCertificateV1.builder()
        .id(UUID.randomUUID().toString())
        .unitId(UNIT_ID)
        .careProviderId(CARE_PROVIDER_ID)
        .patientId(PATIENT_ID)
        .type(CERTIFICATE_TYPE)
        .typeVersion(CERTIFICATE_TYPE_VERSION);
  }

  private static CertificateAnalyticsEventV1Builder eventBuilder() {
    return CertificateAnalyticsEventV1.builder()
        .timestamp(LocalDateTime.now())
        .staffId(STAFF_ID)
        .role(ROLE)
        .unitId(UNIT_ID)
        .careProviderId(CARE_PROVIDER_ID)
        .sessionId(UUID.randomUUID().toString())
        .origin(ORIGIN);
  }
}
