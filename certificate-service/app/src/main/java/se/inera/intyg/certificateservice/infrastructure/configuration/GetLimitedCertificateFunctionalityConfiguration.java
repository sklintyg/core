package se.inera.intyg.certificateservice.infrastructure.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityConfiguration;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetLimitedCertificateFunctionalityConfiguration {

  @Value("${limited.certificate.functionality.configuration.path:}")
  private Resource limitedCertificateFunctionalityConfigurationPath;

  private List<LimitedCertificateFunctionalityConfiguration> limitedCertificateFunctionalityConfigurations;

  public List<LimitedCertificateFunctionalityConfiguration> get() {
    if (limitedCertificateFunctionalityConfigurations == null) {
      final var objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      limitedCertificateFunctionalityConfigurations = new ArrayList<>();
      try (final var resourceAsStream = limitedCertificateFunctionalityConfigurationPath.getInputStream()) {
        limitedCertificateFunctionalityConfigurations = objectMapper.readValue(
            resourceAsStream,
            new TypeReference<>() {
            });
        log.info("Limited Functionality configuration loaded: {}",
            limitedCertificateFunctionalityConfigurations);
      } catch (FileNotFoundException e) {
        log.warn("File not found: {}. Returning empty configuration.",
            limitedCertificateFunctionalityConfigurationPath);
      } catch (Exception e) {
        log.error(
            String.format("Failed to load Limited Functionality configuration. Reason: %s",
                e.getMessage()), e
        );
      }
    }
    return limitedCertificateFunctionalityConfigurations;
  }
}