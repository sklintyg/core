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
  private static final String TEXT_LONGER_THAN_100_40W_WITH_TAB = "AAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAA \tBBBBBBBBBBBBBB BBBBBBBBBBB CCCCCCCCCCCC \tCCCCCCCCCCCCC";
  private static final PdfField LONG_FIELD = PdfField.builder()
      .value(TEXT_LONGER_THAN_100_40)
      .id("ID1")
      .build();
  private static final PdfField LONG_FIELD_WITH_TAB = PdfField.builder()
      .value(TEXT_LONGER_THAN_100_40W_WITH_TAB)
      .id("ID4")
      .build();
  private static final String TEXT_SHORTER_THAN_100_40 = "A B C";
  private static final String TEXT_SHORTER_THAN_100_40_WITH_TAB = "A\tB\tC";
  private static final PdfField SHORT_FIELD = PdfField.builder()
      .value(TEXT_SHORTER_THAN_100_40)
      .id("ID2")
      .build();
  private static final PdfField SHORT_FIELD_WITH_TAB = PdfField.builder()
      .value(TEXT_SHORTER_THAN_100_40_WITH_TAB)
      .id("ID3")
      .build();
  private static final int FONT_SIZE = 10;
  private static final float LINE_HEIGHT = 1.4F;

  TextUtil textUtil = new TextUtil();

  @Test
  void shouldCalculateTextHeightWithNewLines() {
    var actualHeight = textUtil.calculateTextHeight(
        "A short text that contains a new line \n with more text", FONT_SIZE,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertEquals(2 * FONT_SIZE * LINE_HEIGHT, actualHeight);
  }

  @Test
  void shouldCalculateTextHeightWithoutNewLines() {
    var actualHeight = textUtil.calculateTextHeight(
        "A tiny bit longer text that does not contain a new line with more text", FONT_SIZE,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertEquals(FONT_SIZE * LINE_HEIGHT, actualHeight);
  }

  @Test
  void shouldCountLongLineAsMultipleLines() {
    var actualHeight = textUtil.calculateTextHeight(
        "A very long text that fore sure needs to be split into multiple lines A very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple lines",
        FONT_SIZE,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertEquals(4 * FONT_SIZE * LINE_HEIGHT, actualHeight);
  }

  @Test
  void shouldTakeFontSizeIntoConsideration() {
    var actualHeight = textUtil.calculateTextHeight(
        "A very long text that fore sure needs to be split into multiple lines A very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple linesA very long text that fore sure needs to be split into multiple lines",
        20,
        new PDType1Font(
            FontName.HELVETICA), 450);
    assertEquals(16 * FONT_SIZE * LINE_HEIGHT, actualHeight);
  }

  @Test
  void shouldTakeFontTypeIntoConsideration() {
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

  @Nested
  class HandleSpecialCharacters {

    @Test
    void shouldReturnTrueIfOverflowingTextAndTryingToAddMore() {
      final var rectangle = new PDRectangle(FONT_SIZE, FONT_SIZE);

      final var response = textUtil.getOverflowingLines(
          List.of(LONG_FIELD_WITH_TAB),
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
          List.of(SHORT_FIELD, LONG_FIELD_WITH_TAB),
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
          List.of(SHORT_FIELD_WITH_TAB),
          LONG_FIELD,
          RECTANGLE_100_40,
          12,
          new PDType1Font(FontName.HELVETICA)
      );

      assertTrue(response.isPresent());
    }

    @Test
    void shouldCalculateHeightForTextWithTabs() {
      final var textWithTab = "Text with\ttab character";

      final var actualHeight = textUtil.calculateTextHeight(
          textWithTab,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA),
          450
      );

      assertEquals(FONT_SIZE * LINE_HEIGHT, actualHeight);
    }

    @Test
    void shouldWrapLinesWithTabCharacters() {
      final var textWithTab = "Text with\ttab character";

      final var wrappedLines = textUtil.wrapLine(
          textWithTab,
          450,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA)
      );

      assertEquals(1, wrappedLines.size());
      assertEquals("Text with tab character", wrappedLines.getFirst());
    }

    @Test
    void shouldSanitizeTabsButPreserveNewlines() {
      final var textWithTabsAndNewlines = "Line 1\twith tab\nLine 2\twith tab";

      final var actualHeight = textUtil.calculateTextHeight(
          textWithTabsAndNewlines,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA),
          450
      );

      assertEquals(2 * FONT_SIZE * LINE_HEIGHT, actualHeight);
    }

    @Test
    void shouldReplaceCarriageReturnWithSpace() {
      final var textWithCarriageReturn = "Text with\rcarriage return";

      final var wrappedLines = textUtil.wrapLine(
          textWithCarriageReturn,
          450,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA)
      );

      assertEquals(1, wrappedLines.size());
      assertEquals("Text with carriage return", wrappedLines.getFirst());
    }

    @Test
    void shouldReplaceFormFeedWithSpace() {
      final var textWithFormFeed = "Text with\fform feed";

      final var wrappedLines = textUtil.wrapLine(
          textWithFormFeed,
          450,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA)
      );

      assertEquals(1, wrappedLines.size());
      assertEquals("Text with form feed", wrappedLines.getFirst());
    }

    @Test
    void shouldHandleMultipleControlCharacters() {
      final var textWithControlChars = "Text\twith\rmultiple\fcontrol\u0000characters";

      final var wrappedLines = textUtil.wrapLine(
          textWithControlChars,
          450,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA)
      );

      assertEquals(1, wrappedLines.size());
      assertEquals("Text with multiple control characters", wrappedLines.getFirst());
    }

    @Test
    void shouldCalculateCorrectHeightWithControlCharacters() {
      final var textWithControlChars = "Line 1\twith\ttabs\nLine 2\rwith\rcarriage\nLine 3\fwith\fform feeds";

      final var actualHeight = textUtil.calculateTextHeight(
          textWithControlChars,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA),
          450
      );

      assertEquals(3 * FONT_SIZE * LINE_HEIGHT, actualHeight);
    }

    @Test
    void shouldHandleNullText() {
      final var wrappedLines = textUtil.wrapLine(
          null,
          450,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA)
      );

      assertEquals(1, wrappedLines.size());
      assertEquals("", wrappedLines.getFirst());
    }

    @Test
    void shouldPreserveSpacesBetweenWords() {
      final var textWithTabsAndSpaces = "Word1\t  \tWord2";

      final var wrappedLines = textUtil.wrapLine(
          textWithTabsAndSpaces,
          450,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA)
      );

      assertEquals(1, wrappedLines.size());
      assertEquals("Word1    Word2", wrappedLines.getFirst());
    }

    @Test
    void shouldSanitizeNullCharacter() {
      final var textWithNull = "Text\u0000with\u0000null";

      final var wrappedLines = textUtil.wrapLine(
          textWithNull,
          450,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA)
      );

      assertEquals(1, wrappedLines.size());
      assertEquals("Text with null", wrappedLines.getFirst());
    }

    @Test
    void shouldSanitizeMultipleTypesOfControlCharacters() {
      final var textWithMultiple = "A\tB\rC\fD\u0007E";

      final var wrappedLines = textUtil.wrapLine(
          textWithMultiple,
          450,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA)
      );

      assertEquals(1, wrappedLines.size());
      assertEquals("A B C D E", wrappedLines.getFirst());
    }

    @Test
    void shouldHandleEmptyString() {
      final var wrappedLines = textUtil.wrapLine(
          "",
          450,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA)
      );

      assertEquals(1, wrappedLines.size());
      assertEquals("", wrappedLines.getFirst());
    }

    @Test
    void shouldHandleOnlyControlCharacters() {
      final var onlyControlChars = "\t\r\f\u0000\u0007";

      final var wrappedLines = textUtil.wrapLine(
          onlyControlChars,
          450,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA)
      );

      assertEquals(1, wrappedLines.size());
      assertTrue(wrappedLines.getFirst().trim().isEmpty());
    }

    @Test
    void shouldHandleTabsInLongTextThatNeedsWrapping() {
      final var longTextWithTabs = "This is a long text\twith tabs\tthat should wrap\tto multiple lines when the width is limited";

      final var wrappedLines = textUtil.wrapLine(
          longTextWithTabs,
          200,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA)
      );

      assertEquals(2, wrappedLines.size());
    }

    @Test
    void shouldPreserveNewlinesInCalculateTextHeight() {
      final var textWithNewline = "Line 1\nLine 2";

      final var actualHeight = textUtil.calculateTextHeight(
          textWithNewline,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA),
          450
      );

      assertEquals(2 * FONT_SIZE * LINE_HEIGHT, actualHeight);
    }

    @Test
    void shouldFilterOutCharactersNotSupportedByFont() {
      final var textWithUnsupportedChars = "Normal text \uD83D\uDE00 with emoji";

      final var wrappedLines = textUtil.wrapLine(
          textWithUnsupportedChars,
          450,
          FONT_SIZE,
          new PDType1Font(FontName.HELVETICA)
      );

      assertEquals(1, wrappedLines.size());
      assertEquals("Normal text   with emoji", wrappedLines.getFirst());
    }
  }
}