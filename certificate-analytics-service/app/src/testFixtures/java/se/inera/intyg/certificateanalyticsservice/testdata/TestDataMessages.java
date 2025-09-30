package se.inera.intyg.certificateanalyticsservice.testdata;

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
        .messageId("50d64e86-9226-4795-aadd-c00c084c030d")
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
        .id("78a0a279-1197-4cc7-bf6a-899cb5034053")
        .unitId("TSTNMT2321000156-ALMC")
        .careProviderId("TSTNMT2321000156-ALFA")
        .patientId("19401130-6125")
        .type("fk7210")
        .typeVersion("1.0");
  }

  private static CertificateAnalyticsEventV1Builder eventBuilder() {
    return CertificateAnalyticsEventV1.builder()
        .timestamp(LocalDateTime.parse("2025-09-29T17:49:58.616648"))
        .staffId("TSTNMT2321000156-DRAA")
        .role("LAKARE")
        .unitId("TSTNMT2321000156-ALMC")
        .careProviderId("TSTNMT2321000156-ALFA")
        .sessionId("2d02bc34-41f1-42b7-9964-d0659bf369c8")
        .origin("NORMAL");
  }
}
