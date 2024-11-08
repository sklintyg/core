package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class TextUtilTest {

  private static final String TEXT_LONGER_THAN_10_10 = "AAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAA BBBBBBBBBBBBBB BBBBBBBBBBB CCCCCCCCCCCC CCCCCCCCCCCCCCC";
  private static final PdfField LONG_FIELD = PdfField.builder()
      .value(TEXT_LONGER_THAN_10_10)
      .id("ID1")
      .build();
  private static final String TEXT_SHORTER_THAN_10_10 = "A B C";
  private static final PdfField SHORT_FIELD = PdfField.builder()
      .value(TEXT_SHORTER_THAN_10_10)
      .id("ID2")
      .build();

  TextUtil textUtil = new TextUtil();

  @Test
  void shallCalculateTextHeightWithNewLines() throws IOException {
    var actualHeight = textUtil.calculateTextHeight(
        "A short text that contains a new line \n with more text", 10,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertEquals(30F, actualHeight);
  }

  @Test
  void shallCalculateTextHeightWithoutNewLines() throws IOException {
    var actualHeight = textUtil.calculateTextHeight(
        "A tiny bit longer text that does not contain a new line with more text", 10,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertEquals(15F, actualHeight);
  }

  @Test
  void shallCountLongLineAsMultipleLines() throws IOException {
    var actualHeight = textUtil.calculateTextHeight(
        "A very long text that fore sure needs to be split into multiple lines A very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple lines",
        10,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertEquals(60F, actualHeight);
  }

  @Test
  void shallTakeFontSizeIntoConsideration() throws IOException {
    var actualHeight = textUtil.calculateTextHeight(
        "A very long text that fore sure needs to be split into multiple lines A very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple lines",
        20,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertEquals(240F, actualHeight);
  }

  @Test
  void shallTakeFontTypeIntoConsideration() throws IOException {
    final var text = "A long text that needs multiple lines for certain fonts fore sure needs to be split into multiple lines and that is gw";
    var timesFont = textUtil.calculateTextHeight(
        text,
        10,
        new PDType1Font(
            FontName.TIMES_ROMAN), 450);
    var helveticaFont = textUtil.calculateTextHeight(
        text,
        10,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertAll(() -> assertEquals(30F, helveticaFont), () -> assertEquals(15F, timesFont));
  }

  @Nested
  class IsTextOverflowing {

    @Test
    void shouldReturnTrueIfOverflowingTextAndTryingToAddMore() {
      final var rectangle = new PDRectangle(10, 10);
      final var response = textUtil.isTextOverflowing(
          List.of(LONG_FIELD),
          SHORT_FIELD,
          rectangle,
          12,
          new PDType1Font(FontName.HELVETICA)
      );
      assertTrue(response);
    }

  }
}