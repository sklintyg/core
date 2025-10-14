package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;

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

    final var options = PdfGeneratorOptions.builder()
        .citizenFormat(false)
        .hiddenElements(Collections.emptyList())
        .build();

    final var result = config.replacementElementValue(
        options,
        Collections.emptyList()
    );

    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnReplacementValueIfHiddenByIsInHiddenElements() {
    final var config = CitizenPdfConfiguration.builder()
        .hiddenBy(HIDDEN_BY)
        .replacementValue(REPLACEMENT)
        .build();

    final var options = PdfGeneratorOptions.builder()
        .citizenFormat(true)
        .hiddenElements(List.of(HIDDEN_BY))
        .build();

    final var result = config.replacementElementValue(
        options,
        Collections.emptyList()
    );

    assertTrue(result.isPresent());
  }

  @Test
  void shouldReturnReplacementValueIfShouldHidePredicateIsTrue() {
    final var config = CitizenPdfConfiguration.builder()
        .shouldHide(elementData -> true)
        .replacementValue(REPLACEMENT)
        .build();

    final var options = PdfGeneratorOptions.builder()
        .citizenFormat(true)
        .hiddenElements(Collections.emptyList())
        .build();

    final var result = config.replacementElementValue(
        options,
        Collections.emptyList()
    );

    assertTrue(result.isPresent());
  }

  @Test
  void shouldReturnEmptyIfNeitherHiddenByNorShouldHidePredicateIsTrue() {
    final var config = CitizenPdfConfiguration.builder()
        .hiddenBy(HIDDEN_BY)
        .shouldHide(elementData -> false)
        .replacementValue(REPLACEMENT)
        .build();

    final var options = PdfGeneratorOptions.builder()
        .citizenFormat(true)
        .hiddenElements(List.of(new ElementId("otherElement")))
        .build();

    final var result = config.replacementElementValue(
        options,
        Collections.emptyList()
    );

    assertTrue(result.isEmpty());
  }
}

