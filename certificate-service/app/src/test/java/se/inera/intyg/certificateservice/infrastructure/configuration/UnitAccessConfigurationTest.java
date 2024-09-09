package se.inera.intyg.certificateservice.infrastructure.configuration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UnitAccessConfigurationTest {

  private static final String UNIT_ACCESS_WITH_VALUES = Paths.get("src", "test", "resources",
      "unitaccess",
      "unit-access-with-values.json").toString();
  private static final String INVALID_PATH = "InvalidPath";
  private static final String UNIT_ACCESS_WITH_NULL_VALUES = Paths.get("src", "test", "resources",
      "unitaccess",
      "unit-access-with-fromdate-null.json").toString();

  private UnitAccessConfiguration unitAccessConfiguration;

  @BeforeEach
  void setUp() {
    unitAccessConfiguration = new UnitAccessConfiguration();
  }

  @Test
  void shallParseFileWithValues() {
    ReflectionTestUtils.setField(unitAccessConfiguration, "unitAccessConfigurationPath",
        UNIT_ACCESS_WITH_VALUES);
    final var certificateAccessConfigurations = unitAccessConfiguration.get();
    assertTrue(true);
  }
}