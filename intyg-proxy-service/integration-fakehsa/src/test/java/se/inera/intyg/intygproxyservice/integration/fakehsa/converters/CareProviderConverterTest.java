package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;

class CareProviderConverterTest {

  private final CareProviderConverter careProviderConverter = new CareProviderConverter();

  @Test
  void shouldSetId() {
    final var expected = "ID";
    final var parsed = ParsedCareProvider.builder().id(expected).build();

    final var response = careProviderConverter.convert(parsed);

    assertEquals(expected, response.getHealthCareProviderHsaId());
  }

  @Test
  void shouldSetName() {
    final var expected = "NAME";
    final var parsed = ParsedCareProvider.builder().name(expected).build();

    final var response = careProviderConverter.convert(parsed);

    assertEquals(expected, response.getHealthCareProviderName());
  }

}