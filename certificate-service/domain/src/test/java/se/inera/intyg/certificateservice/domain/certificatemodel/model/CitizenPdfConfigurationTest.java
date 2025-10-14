package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;

class CitizenPdfConfigurationTest {

  private static final ElementSimplifiedValueText REPLACEMENT = ElementSimplifiedValueText.builder()
      .text("REPLACEMENT")
      .build();
  private static final ElementId HIDDEN_BY = new ElementId("HIDDEN_BY");

  @Test
  void shouldReturnEmptyIfNotCitizenFormat() {
    final var config = CitizenPdfConfiguration.builder()
        .hiddenBy(HIDDEN_BY)
        .build();

    final var result = config.replacementElementValue(
        null,
        null,
        false
    );

    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnReplacementValueIfHiddenByIsInHiddenElements() {
    var config = CitizenPdfConfiguration.builder()
        .hiddenBy(HIDDEN_BY)
        .replacementValue(REPLACEMENT)
        .build();

    var result = config.replacementElementValue(
        List.of(HIDDEN_BY),
        null,
        true
    );

    assertTrue(result.isPresent());
  }

  @Test
  void shouldReturnReplacementValueIfShouldHidePredicateIsTrue() {
    final var config = CitizenPdfConfiguration.builder()
        .shouldHide(elementData -> true)
        .replacementValue(REPLACEMENT)
        .build();
    var result = config.replacementElementValue(
        null,
        List.of(),
        true
    );

    assertTrue(result.isPresent());
  }

  @Test
  void shouldReturnEmptyIfNeitherHiddenByNorShouldHidePredicateIsTrue() {
    var config = CitizenPdfConfiguration.builder()
        .hiddenBy(HIDDEN_BY)
        .shouldHide(elementData -> false)
        .replacementValue(REPLACEMENT)
        .build();
    var result = config.replacementElementValue(
        List.of(new ElementId("otherElement")),
        List.of(),
        true
    );

    assertTrue(result.isEmpty());
  }
}