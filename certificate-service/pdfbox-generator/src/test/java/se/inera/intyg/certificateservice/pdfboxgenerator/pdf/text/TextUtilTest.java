package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class TextUtilTest {

  public static final PDRectangle RECTANGLE_100_40 = new PDRectangle(100, 56);
  private static final String TEXT_LONGER_THAN_100_40 = "AAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAA BBBBBBBBBBBBBB BBBBBBBBBBB CCCCCCCCCCCC CCCCCCCCCCCCCCC";
  private static final PdfField LONG_FIELD = PdfField.builder()
      .value(TEXT_LONGER_THAN_100_40)
      .id("ID1")
      .build();
  private static final String TEXT_SHORTER_THAN_100_40 = "A B C";
  private static final PdfField SHORT_FIELD = PdfField.builder()
      .value(TEXT_SHORTER_THAN_100_40)
      .id("ID2")
      .build();
  private static final int FONT_SIZE = 10;
  private static final float LINE_HEIGHT = 1.4F;

  TextUtil textUtil = new TextUtil();

  @Test
  void shallCalculateTextHeightWithNewLines() {
    var actualHeight = textUtil.calculateTextHeight(
        "A short text that contains a new line \n with more text", FONT_SIZE,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertEquals(2 * FONT_SIZE * LINE_HEIGHT, actualHeight);
  }

  @Test
  void shallCalculateTextHeightWithoutNewLines() {
    var actualHeight = textUtil.calculateTextHeight(
        "A tiny bit longer text that does not contain a new line with more text", FONT_SIZE,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertEquals(FONT_SIZE * LINE_HEIGHT, actualHeight);
  }

  @Test
  void shallCountLongLineAsMultipleLines() {
    var actualHeight = textUtil.calculateTextHeight(
        "A very long text that fore sure needs to be split into multiple lines A very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple lines",
        FONT_SIZE,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertEquals(4 * FONT_SIZE * LINE_HEIGHT, actualHeight);
  }

  @Test
  void shallTakeFontSizeIntoConsideration() {
    var actualHeight = textUtil.calculateTextHeight(
        "A very long text that fore sure needs to be split into multiple lines A very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple lines",
        20,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertEquals(16 * FONT_SIZE * LINE_HEIGHT, actualHeight);
  }

  @Test
  void shallTakeFontTypeIntoConsideration() {
    final var text = "A long text that needs multiple lines for certain fonts fore sure needs to be split into multiple lines and that is gw";
    var timesFont = textUtil.calculateTextHeight(
        text,
        FONT_SIZE,
        new PDType1Font(
            FontName.TIMES_ROMAN), 450);
    var helveticaFont = textUtil.calculateTextHeight(
        text,
        FONT_SIZE,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertAll(() -> assertEquals(2 * FONT_SIZE * LINE_HEIGHT, helveticaFont),
        () -> assertEquals(FONT_SIZE * LINE_HEIGHT, timesFont));
  }

  @Nested
  class IsTextOverflowing {

    @Test
    void shouldReturnTrueIfOverflowingTextAndTryingToAddMore() {
      final var rectangle = new PDRectangle(FONT_SIZE, FONT_SIZE);

      final var response = textUtil.getOverflowingLines(
          List.of(LONG_FIELD),
          SHORT_FIELD,
          rectangle,
          12,
          new PDType1Font(FontName.HELVETICA)
      );

      assertTrue(response.isPresent());
    }

    @Test
    void shouldReturnTrueIfOverflowingTextInSeveralPartsAndTryingToAddMore() {

      final var response = textUtil.getOverflowingLines(
          List.of(SHORT_FIELD, LONG_FIELD),
          LONG_FIELD,
          RECTANGLE_100_40,
          12,
          new PDType1Font(FontName.HELVETICA)
      );

      assertTrue(response.isPresent());
    }

    @Test
    void shouldReturnFalseIfNotOverflowingText() {
      final var response = textUtil.getOverflowingLines(
          List.of(SHORT_FIELD),
          SHORT_FIELD,
          RECTANGLE_100_40,
          12,
          new PDType1Font(FontName.HELVETICA)
      );

      assertFalse(response.isPresent());
    }

    @Test
    void shouldReturnTrueIfNotOverflowingTextButAddingOverflow() {
      final var response = textUtil.getOverflowingLines(
          List.of(SHORT_FIELD),
          LONG_FIELD,
          RECTANGLE_100_40,
          12,
          new PDType1Font(FontName.HELVETICA)
      );

      assertTrue(response.isPresent());
    }
  }
}