package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.stream.Stream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
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
    void shallReplaceProblemHyphensWithRegularHyphen(Map<String, String> problemHyphenMap,
        String description) {
      String problemHyphen = problemHyphenMap.keySet().iterator().next();
      String replacement = problemHyphenMap.get(problemHyphen);
      final var pdfField = PdfField.builder()
          .id("testId")
          .value("text" + problemHyphen + "text")
          .build();

      final var result = pdfField.sanitizedValue(new PDType1Font(FontName.HELVETICA));

      assertEquals("text" + replacement + "text", result);
    }

    private static Stream<Arguments> provideProblemHyphens() {
      return Stream.of(
          Arguments.of(Map.of("\u2010", "-"), "hyphen"),
          Arguments.of(Map.of("\u2011", "-"), "non-breaking hyphen"),
          Arguments.of(Map.of("\u2012", "-"), "figure dash"),
          Arguments.of(Map.of("\u2013", "-"), "en dash"),
          Arguments.of(Map.of("\u2014", "-"), "em dash"),
          Arguments.of(Map.of("\u2015", "-"), "horizontal bar"),
          Arguments.of(Map.of("\u2212", "-"), "minus"),
          Arguments.of(Map.of("\u2192", "->"), "rightwards arrow"),
          Arguments.of(Map.of("\u2190", "<-"), "leftwards arrow"),
          Arguments.of(Map.of("\u2194", "<->"), "left right arrow")
      );
    }

    @ParameterizedTest()
    @MethodSource("provideTextValues")
    void shallReturnSanitizedValue(String inputValue, String expectedValue) {
      final var pdfField = PdfField.builder()
          .id("testId")
          .value(inputValue)
          .build();

      final var result = pdfField.sanitizedValue(new PDType1Font(FontName.HELVETICA));

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

      assertEquals("", pdfField.sanitizedValue(new PDType1Font(FontName.HELVETICA)));
    }
  }
}