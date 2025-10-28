package se.inera.intyg.certificateservice.infrastructure.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.CertificateInactiveConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.InactiveCertificateActionConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.InactiveCertificateConfiguration;

@ExtendWith(MockitoExtension.class)
class GetInactiveCertificateConfigurationTest {

  private static final String INACTIVE_CERTIFICATE_WITH_SEND_CONFIG = Paths.get("src", "test",
      "resources",
      "inactive",
      "inactive-certificate-with-send-config.json").toString();

  private static final String INACTIVE_CERTIFICATE_WITHOUT_ACTIONS = Paths.get("src", "test",
      "resources",
      "inactive",
      "inactive-certificate-without-actions.json").toString();

  private GetInactiveCertificateConfiguration getInactiveCertificateConfiguration;

  @BeforeEach
  void setUp() {
    getInactiveCertificateConfiguration = new GetInactiveCertificateConfiguration();
  }

  @Test
  void shallParseFileWithActionSend() {
    ReflectionTestUtils.setField(getInactiveCertificateConfiguration,
        "inactiveCertificateConfigurationPath",
        INACTIVE_CERTIFICATE_WITH_SEND_CONFIG);

    final var expectedInactiveCertificateConfiguration = List.of(
        CertificateInactiveConfiguration.builder()
            .certificateType("ts8071")
            .version(List.of("1.0"))
            .configuration(
                InactiveCertificateConfiguration.builder()
                    .actions(
                        List.of(
                            InactiveCertificateActionConfiguration.builder()
                                .type("SEND")
                                .untilDateTime(LocalDateTime.of(2024, 8, 1, 8, 0, 0))
                                .build()
                        )
                    )
                    .build()
            )
            .build()
    );

    final var actualCertificateAccessConfigurations = getInactiveCertificateConfiguration.get();
    assertEquals(expectedInactiveCertificateConfiguration, actualCertificateAccessConfigurations);
  }

  @Test
  void shallParseFileWithoutActions() {
    ReflectionTestUtils.setField(getInactiveCertificateConfiguration,
        "inactiveCertificateConfigurationPath",
        INACTIVE_CERTIFICATE_WITHOUT_ACTIONS);

    final var expectedInactiveCertificateConfiguration = List.of(
        CertificateInactiveConfiguration.builder()
            .certificateType("ts8071")
            .version(List.of("1.0"))
            .configuration(
                InactiveCertificateConfiguration.builder()
                    .actions(Collections.emptyList())
                    .build()
            )
            .build()
    );

    final var actualCertificateAccessConfigurations = getInactiveCertificateConfiguration.get();
    assertEquals(expectedInactiveCertificateConfiguration, actualCertificateAccessConfigurations);
  }
}