package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;

class HealthCareProviderConverterTest {

  private HealthCareProviderConverter healthCareProviderConverter;

  private static final String VALUE = "value";

  @BeforeEach
  void setUp() {
    healthCareProviderConverter = new HealthCareProviderConverter();
  }

  @Nested
  class ConvertId {

    @Test
    void shouldConvertId() {
      final var parsedCareProvider = ParsedCareProvider.builder()
          .id(VALUE)
          .build();
      final var result = healthCareProviderConverter.convert(parsedCareProvider);
      assertEquals(parsedCareProvider.getId(), result.getHealthCareProviderHsaId());
    }

    @Test
    void shouldNotConvertId() {
      final var parsedCareProvider = ParsedCareProvider.builder()
          .build();
      final var result = healthCareProviderConverter.convert(parsedCareProvider);
      assertNull(result.getHealthCareProviderHsaId());
    }
  }

  @Nested
  class ConvertName {

    @Test
    void shouldConvertName() {
      final var parsedCareProvider = ParsedCareProvider.builder()
          .name(VALUE)
          .build();
      final var result = healthCareProviderConverter.convert(parsedCareProvider);
      assertEquals(parsedCareProvider.getName(), result.getHealthCareProviderName());
    }

    @Test
    void shouldNotConvertName() {
      final var parsedCareProvider = ParsedCareProvider.builder()
          .build();
      final var result = healthCareProviderConverter.convert(parsedCareProvider);
      assertNull(result.getHealthCareProviderName());
    }
  }
}
