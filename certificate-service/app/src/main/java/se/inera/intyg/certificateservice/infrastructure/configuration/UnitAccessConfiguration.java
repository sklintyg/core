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
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessConfiguration;

@Slf4j
@Component
@RequiredArgsConstructor
public class UnitAccessConfiguration {

  @Value("${unit.access.configuration.path:}")
  private Resource unitAccessConfigurationPath;

  private List<CertificateAccessConfiguration> certificateAccessConfigurations;

  public List<CertificateAccessConfiguration> get() {
    if (certificateAccessConfigurations == null) {
      final var objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      certificateAccessConfigurations = new ArrayList<>();
      try (final var resourceAsStream = unitAccessConfigurationPath.getInputStream()) {
        certificateAccessConfigurations = objectMapper.readValue(
            resourceAsStream,
            new TypeReference<>() {
            });
        log.info("Certificate Unit Access was loaded with configuration: {}",
            certificateAccessConfigurations);
      } catch (FileNotFoundException e) {
        log.warn("File not found: {}. Returning empty configuration.",
            unitAccessConfigurationPath);
      } catch (Exception e) {
        log.error(
            String.format("Failed to load Certificate Unit Access configuration. Reason: %s",
                e.getMessage()), e
        );
      }
    }
    return certificateAccessConfigurations;
  }
}