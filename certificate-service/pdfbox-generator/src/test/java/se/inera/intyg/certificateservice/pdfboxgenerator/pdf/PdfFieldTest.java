package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PdfFieldTest {

  @Nested
  class SanitizedValueTest {

    @ParameterizedTest()
    @MethodSource("provideProblemHyphens")
    void shallReplaceProblemHyphensWithRegularHyphen(String problemHyphen, String description) {
      final var pdfField = PdfField.builder()
          .id("testId")
          .value("text" + problemHyphen + "text")
          .build();

      final var result = pdfField.sanitizedValue();

      assertEquals("text-text", result);
    }

    private static Stream<Arguments> provideProblemHyphens() {
      return Stream.of(
          Arguments.of("\u2010", "hyphen"),
          Arguments.of("\u2011", "non-breaking hyphen"),
          Arguments.of("\u2012", "figure dash"),
          Arguments.of("\u2013", "en dash"),
          Arguments.of("\u2014", "em dash"),
          Arguments.of("\u2015", "horizontal bar")
      );
    }

    @ParameterizedTest()
    @MethodSource("provideTextValues")
    void shallReturnSanitizedValue(String inputValue, String expectedValue) {
      final var pdfField = PdfField.builder()
          .id("testId")
          .value(inputValue)
          .build();

      final var result = pdfField.sanitizedValue();

      assertEquals(expectedValue, result);
    }

    private static Stream<Arguments> provideTextValues() {
      return Stream.of(
          Arguments.of("normal text", "normal text"),
          Arguments.of("text\u2013with\u2014multiple\u2010hyphens", "text-with-multiple-hyphens"),
          Arguments.of("", ""),
          Arguments.of("-", "-"),
          Arguments.of("already-has-regular-hyphen", "already-has-regular-hyphen")
      );
    }

    @Test
    void shallHandleNullValue() {
      final var pdfField = PdfField.builder()
          .id("testId")
          .value(null)
          .build();

      assertEquals("", pdfField.sanitizedValue());
    }
  }
}