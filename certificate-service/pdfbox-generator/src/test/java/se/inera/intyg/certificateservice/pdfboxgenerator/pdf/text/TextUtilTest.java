package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

      final var response = textUtil.getOverflowingLines(
          List.of(LONG_FIELD),
          SHORT_FIELD,
          RECTANGLE_100_40,
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

    @Test
    void shouldReturnMultipleElementsIfOverflowingOverSeveralPages() {
      final var veryLongText = TEXT_LONGER_THAN_100_40.repeat(10);
      final var longField = PdfField.builder()
          .value(veryLongText)
          .id("ID5")
          .build();

      final var response = textUtil.getOverflowingLines(
          List.of(longField),
          LONG_FIELD,
          RECTANGLE_100_40,
          12,
          new PDType1Font(FontName.HELVETICA)
      ).orElseThrow();

      assertTrue(response.overflowPages().size() > 1);
    }

    @Test
    void shouldThrowExceptionIfAvailableLinesIsNegative() {
      final var fields = List.of(LONG_FIELD);
      final var rectangle = new PDRectangle(FONT_SIZE, FONT_SIZE);
      final var font = new PDType1Font(FontName.HELVETICA);

      assertThrows(IllegalStateException.class, () -> textUtil.getOverflowingLines(
          fields,
          LONG_FIELD,
          rectangle,
          12,
          font
      ));
    }
  }

  @Nested
  class HandleSpecialCharacters {

    @Test
    void shouldReturnTrueIfOverflowingTextAndTryingToAddMore() {

      final var response = textUtil.getOverflowingLines(
          List.of(LONG_FIELD_WITH_TAB),
          SHORT_FIELD,
          RECTANGLE_100_40,
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
    void shouldSanitizeMultipleConsecutiveControlCharacters() {
      final var textWithConsecutive = "A\t\t\t\tB\r\rC\f\fD\u0000\u0000E";

      final var wrappedLines = textUtil.wrapLine(
          textWithConsecutive,
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

  @Nested
  class NormalizePrintableCharacters {

    @Test
    void shouldReturnSameTextWhenAllCharactersAreSupported() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var text = "Hello World 123";

      final var result = TextUtil.normalizePrintableCharacters(text, font);

      assertEquals("Hello World 123", result);
    }

    @Test
    void shouldReplaceUnsupportedCharacterWithSpace() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithUnsupported = "Hello\u2665World"; // Heart symbol

      final var result = TextUtil.normalizePrintableCharacters(textWithUnsupported, font);

      assertEquals("Hello World", result);
    }

    @Test
    void shouldReplaceHyphenVariantsWithRegularHyphen() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithHyphens = "Hello\u2010\u2011\u2012\u2013\u2014\u2015World";

      final var result = TextUtil.normalizePrintableCharacters(textWithHyphens, font);

      assertEquals("Hello------World", result);
    }

    @Test
    void shouldReplaceMinusSignWithHyphen() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithMinus = "Value\u22125";

      final var result = TextUtil.normalizePrintableCharacters(textWithMinus, font);

      assertEquals("Value-5", result);
    }

    @Test
    void shouldReplaceArrowsWithTextRepresentation() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithArrows = "A\u2192B\u2190C\u2194D";

      final var result = TextUtil.normalizePrintableCharacters(textWithArrows, font);

      assertEquals("A->B<-C<->D", result);
    }

    @Test
    void shouldHandleEmptyString() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var emptyText = "";

      final var result = TextUtil.normalizePrintableCharacters(emptyText, font);

      assertEquals("", result);
    }

    @Test
    void shouldHandleStringWithOnlyUnsupportedCharacters() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithUnsupported = "\u2665\u2666\u2663\u2660";

      final var result = TextUtil.normalizePrintableCharacters(textWithUnsupported, font);

      assertEquals("    ", result);
    }

    @Test
    void shouldHandleMixedSupportedAndUnsupportedCharacters() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var mixedText = "Hello\u2665World\u2192Test";

      final var result = TextUtil.normalizePrintableCharacters(mixedText, font);

      assertEquals("Hello World->Test", result);
    }

    @Test
    void shouldReplaceAllHyphenVariants() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var allHyphens = "\u2010\u2011\u2012\u2013\u2014\u2015\u2212";

      final var result = TextUtil.normalizePrintableCharacters(allHyphens, font);

      assertEquals("-------", result);
    }

    @Test
    void shouldHandleUnicodeCharactersCorrectly() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithUnicode = "Price: 100\u2192200";

      final var result = TextUtil.normalizePrintableCharacters(textWithUnicode, font);

      assertEquals("Price: 100->200", result);
    }

    @Test
    void shouldPreserveSpacesAndNewlines() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithWhitespace = "Line 1\nLine 2   with spaces";

      final var result = TextUtil.normalizePrintableCharacters(textWithWhitespace, font);

      assertEquals("Line 1\nLine 2   with spaces", result);
    }

    @Test
    void shouldHandleMultipleReplacementsInOneString() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var complexText = "Text\u2013with\u2014different\u2212dashes\u2192and\u2190arrows";

      final var result = TextUtil.normalizePrintableCharacters(complexText, font);

      assertEquals("Text-with-different-dashes->and<-arrows", result);
    }

    @Test
    void shouldHandleNumericText() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var numericText = "123.456";

      final var result = TextUtil.normalizePrintableCharacters(numericText, font);

      assertEquals("123.456", result);
    }

    @Test
    void shouldReplaceLeftRightArrowCorrectly() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithBidirectionalArrow = "A\u2194B";

      final var result = TextUtil.normalizePrintableCharacters(textWithBidirectionalArrow, font);

      assertEquals("A<->B", result);
    }

    @Test
    void shouldHandleConsecutiveUnsupportedCharacters() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var consecutiveUnsupported = "Hello\u2665\u2666\u2663World";

      final var result = TextUtil.normalizePrintableCharacters(consecutiveUnsupported, font);

      assertEquals("Hello   World", result);
    }

    @Test
    void shouldHandleTextWithSpecialPunctuation() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithPunctuation = "Hello, World! How are you?";

      final var result = TextUtil.normalizePrintableCharacters(textWithPunctuation, font);

      assertEquals("Hello, World! How are you?", result);
    }

    @Test
    void shouldNormalizeEnDashAndEmDash() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithDashes = "Range: 2010\u20132020, Note\u2014important";

      final var result = TextUtil.normalizePrintableCharacters(textWithDashes, font);

      assertEquals("Range: 2010-2020, Note-important", result);
    }

    @Test
    void shouldHandleNonBreakingHyphen() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithNonBreakingHyphen = "well\u2011known";

      final var result = TextUtil.normalizePrintableCharacters(textWithNonBreakingHyphen, font);

      assertEquals("well-known", result);
    }

    @Test
    void shouldHandleFigureDash() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithFigureDash = "Phone: 555\u20121234";

      final var result = TextUtil.normalizePrintableCharacters(textWithFigureDash, font);

      assertEquals("Phone: 555-1234", result);
    }

    @Test
    void shouldHandleHorizontalBar() {
      final var font = new PDType1Font(FontName.HELVETICA);
      final var textWithHorizontalBar = "Section\u2015Break";

      final var result = TextUtil.normalizePrintableCharacters(textWithHorizontalBar, font);

      assertEquals("Section-Break", result);
    }
  }
}