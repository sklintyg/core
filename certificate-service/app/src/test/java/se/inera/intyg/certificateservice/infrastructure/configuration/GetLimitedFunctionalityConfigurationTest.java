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
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.InactiveCertificateActionConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.InactiveCertificateConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.LimitedFunctionalityConfiguration;

@ExtendWith(MockitoExtension.class)
class GetLimitedFunctionalityConfigurationTest {

  private static final String LIMITED_FUNCTIONALITY_WITH_SEND_CONFIG = Paths.get("src", "test",
      "resources",
      "limitedfunctionality",
      "limited-functionality-with-send-config.json").toString();

  private static final String LIMITED_FUNCTIONALITY_WITHOUT_ACTIONS = Paths.get("src", "test",
      "resources",
      "limitedfunctionality",
      "limited-functionality-without-actions.json").toString();

  private GetLimitedFunctionalityConfiguration getLimitedFunctionalityConfiguration;

  @BeforeEach
  void setUp() {
    getLimitedFunctionalityConfiguration = new GetLimitedFunctionalityConfiguration();
  }

  @Test
  void shallParseFileWithActionSend() {
    ReflectionTestUtils.setField(getLimitedFunctionalityConfiguration,
        "limitedFunctionalityConfigurationPath",
        LIMITED_FUNCTIONALITY_WITH_SEND_CONFIG);

    final var expectedInactiveCertificateConfiguration = List.of(
        LimitedFunctionalityConfiguration.builder()
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

    final var actualCertificateAccessConfigurations = getLimitedFunctionalityConfiguration.get();
    assertEquals(expectedInactiveCertificateConfiguration, actualCertificateAccessConfigurations);
  }

  @Test
  void shallParseFileWithoutActions() {
    ReflectionTestUtils.setField(getLimitedFunctionalityConfiguration,
        "limitedFunctionalityConfigurationPath",
        LIMITED_FUNCTIONALITY_WITHOUT_ACTIONS);

    final var expectedInactiveCertificateConfiguration = List.of(
        LimitedFunctionalityConfiguration.builder()
            .certificateType("ts8071")
            .version(List.of("1.0"))
            .configuration(
                InactiveCertificateConfiguration.builder()
                    .actions(Collections.emptyList())
                    .build()
            )
            .build()
    );

    final var actualCertificateAccessConfigurations = getLimitedFunctionalityConfiguration.get();
    assertEquals(expectedInactiveCertificateConfiguration, actualCertificateAccessConfigurations);
  }
}