package se.inera.intyg.certificateanalyticsservice.application.messages.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.AnalyticsMessageConverter;

@Component
@RequiredArgsConstructor
public class AnalyticsMessageConverterProvider {

  private final List<AnalyticsMessageConverter> analyticsMessageConverters;

  public AnalyticsMessageConverter converter(String type, String schemaVersion) {
    return this.analyticsMessageConverters.stream()
        .filter(c -> c.canConvert(type, schemaVersion))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
                "No converter found for type '%s' and schema version '%s'"
                    .formatted(type, schemaVersion)
            )
        );
  }
}
