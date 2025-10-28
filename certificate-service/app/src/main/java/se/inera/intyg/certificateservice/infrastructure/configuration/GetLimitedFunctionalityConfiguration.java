package se.inera.intyg.certificateservice.infrastructure.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.configuration.limitedfunctionality.dto.LimitedFunctionalityConfiguration;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetLimitedFunctionalityConfiguration {

  @Value("${limited.functionality.configuration.path:}")
  private String limitedFunctionalityConfigurationPath;

  private List<LimitedFunctionalityConfiguration> limitedFunctionalityConfigurations;

  public List<LimitedFunctionalityConfiguration> get() {
    if (limitedFunctionalityConfigurations == null) {
      final var objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      limitedFunctionalityConfigurations = new ArrayList<>();
      try (final var resourceAsStream = new FileInputStream(
          limitedFunctionalityConfigurationPath)) {
        limitedFunctionalityConfigurations = objectMapper.readValue(
            resourceAsStream,
            new TypeReference<>() {
            });
        log.info("Limited Functionality configuration loaded: {}",
            limitedFunctionalityConfigurations);
      } catch (FileNotFoundException e) {
        log.warn("File not found: {}. Returning empty configuration.",
            limitedFunctionalityConfigurationPath);
      } catch (Exception e) {
        log.error(
            String.format("Failed to load Limited Functionality configuration. Reason: %s",
                e.getMessage()), e
        );
      }
    }
    return limitedFunctionalityConfigurations;
  }
}