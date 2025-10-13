package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIcf;

class ElementValueIcfTest {

  @Nested
  class FormatIcfValueTextTests {

    @Test
    void shouldReturnTextIfIcfCodesIsEmpty() {
      final var expectedValue = "expectedValue";
      final var elementValueIcf = ElementValueIcf.builder()
          .text(expectedValue)
          .build();

      final var result = elementValueIcf.formatIcfValueText(null);
      assertEquals(expectedValue, result);
    }

    @Test
    void shouldReturnFormattedTextIfIcdCodesHasValue() {
      final var expectedText = """
          collectionsLabel icfCode1 - icfCode2
          
          text
          """;
      final var elementValueIcf = ElementValueIcf.builder()
          .text("text")
          .icfCodes(List.of("icfCode1", "icfCode2"))
          .build();

      final var elementConfigurationIcf = ElementConfigurationIcf.builder()
          .collectionsLabel("collectionsLabel")
          .build();

      final var result = elementValueIcf.formatIcfValueText(elementConfigurationIcf);
      assertEquals(expectedText, result);
    }
  }
}