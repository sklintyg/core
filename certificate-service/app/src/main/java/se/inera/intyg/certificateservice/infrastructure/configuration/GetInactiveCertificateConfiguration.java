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
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.CertificateInactiveConfiguration;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetInactiveCertificateConfiguration {

  @Value("${inactive.certificate.configuration.path:}")
  private String inactiveCertificateConfigurationPath;

  private List<CertificateInactiveConfiguration> inactiveCertificateConfigurations;

  public List<CertificateInactiveConfiguration> get() {
    if (inactiveCertificateConfigurations == null) {
      final var objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      inactiveCertificateConfigurations = new ArrayList<>();
      try (final var resourceAsStream = new FileInputStream(inactiveCertificateConfigurationPath)) {
        inactiveCertificateConfigurations = objectMapper.readValue(
            resourceAsStream,
            new TypeReference<>() {
            });
        log.info("Inactive Certificate was loaded with configuration: {}",
            inactiveCertificateConfigurations);
      } catch (FileNotFoundException e) {
        log.warn("File not found: {}. Returning empty configuration.",
            inactiveCertificateConfigurationPath);
      } catch (Exception e) {
        log.error(
            String.format("Failed to load Inactive Certificate configuration. Reason: %s",
                e.getMessage()), e
        );
      }
    }
    return inactiveCertificateConfigurations;
  }
}