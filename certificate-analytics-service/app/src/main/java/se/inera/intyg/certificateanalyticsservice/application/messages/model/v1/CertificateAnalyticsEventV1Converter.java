package se.inera.intyg.certificateanalyticsservice.application.messages.model.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UncheckedIOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.AnalyticsMessageConverter;

@Component
@RequiredArgsConstructor
public class CertificateAnalyticsEventV1Converter implements AnalyticsMessageConverter {

  private static final String TYPE = "certificate.analytics.event";
  private static final String SCHEMA_VERSION = "v1";

  private final ObjectMapper objectMapper;

  public boolean canConvert(String type, String schemaVersion) {
    return TYPE.equals(type) && SCHEMA_VERSION.equals(schemaVersion);
  }

  public CertificateAnalyticsEventMessageV1 convert(String message) {
    try {
      return objectMapper.readValue(message, CertificateAnalyticsEventMessageV1.class);
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException(e);
    }
  }
}
