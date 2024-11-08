package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.junit.jupiter.api.Test;

class TextUtilTest {

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
}