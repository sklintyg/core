package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcf;

class ElementConfigurationIcfTest {

  @Test
  void shouldReturnSimplifiedValue() {
    final var text = "Test text for this test";
    final var value = ElementValueIcf.builder()
        .text(text)
        .icfCodes(List.of("code1", "code2"))
        .build();

    final var result = """
        %s
        
        %s""".formatted(
        String.join(" - ", value.icfCodes()),
        value.text());

    final var config = ElementConfigurationIcf.builder()
        .build();

    assertEquals(
        result,
        ((ElementSimplifiedValueText) config.simplified(value).get()).text()
    );
  }

  @Test
  void shouldReturnSimplifiedValueIfEmpty() {
    final var expected = Optional.of(
        ElementSimplifiedValueText.builder()
            .text("Ej angivet")
            .build()
    );
    final var config = ElementConfigurationIcf.builder()
        .build();

    assertEquals(expected, config.simplified(config.emptyValue()));
  }

}