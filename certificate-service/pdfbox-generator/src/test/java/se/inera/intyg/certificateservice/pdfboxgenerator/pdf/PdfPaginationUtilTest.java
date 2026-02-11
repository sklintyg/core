package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory.TextFieldAppearanceFactory;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.OverFlowLineSplit;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextUtil;

@ExtendWith(MockitoExtension.class)
class PdfPaginationUtilTest {

  @Mock
  private TextUtil textUtil;
  @Mock
  private TextFieldAppearanceFactory textFieldAppearanceFactory;
  @Mock
  private CertificatePdfContext context;
  @Mock
  private PDVariableText overflowField;

  private PdfPaginationUtil pdfPaginationUtil;
  private PDFont font;
  private static final float FONT_SIZE = 10.0f;
  private static final String DEFAULT_APPEARANCE = "/Helvetica 10 Tf";

  @BeforeEach
  void setUp() {
    pdfPaginationUtil = new PdfPaginationUtil(textUtil, textFieldAppearanceFactory);
    font = new PDType1Font(FontName.HELVETICA);
    when(textFieldAppearanceFactory.create(any())).thenReturn(
        Optional.of(new TextFieldAppearance(overflowField)));
  }

  @Test
  void shouldReturnEmptyListWhenNoFieldsProvided() {
    final var result = pdfPaginationUtil.paginateFields(context, List.of(), overflowField);

    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnSinglePageWhenAllFieldsFitOnOnePage() {
    setupOverflowField(new PDRectangle(100, 100));
    setupContext();

    final var field1 = PdfField.builder()
        .id("field1")
        .value("Short text")
        .build();
    final var field2 = PdfField.builder()
        .id("field2")
        .value("Another short text")
        .build();
    final var fields = List.of(field1, field2);

    when(textUtil.getOverflowingLines(anyList(), eq(field1), any(PDRectangle.class), anyFloat(),
        any(PDFont.class)))
        .thenReturn(Optional.empty());
    when(textUtil.getOverflowingLines(anyList(), eq(field2), any(PDRectangle.class), anyFloat(),
        any(PDFont.class)))
        .thenReturn(Optional.empty());

    final var result = pdfPaginationUtil.paginateFields(context, fields, overflowField);

    assertAll(
        () -> assertEquals(1, result.size(), "Should be a single page"),
        () -> assertEquals(2, result.getFirst().size(), "Page should contain both fields"),
        () -> assertEquals(field1, result.getFirst().getFirst(), "First field should match"),
        () -> assertEquals(field2, result.getFirst().get(1), "Second field should match")
    );
  }

  @Test
  void shouldSplitIntoMultiplePagesWhenFieldOverflows() {
    setupOverflowField(new PDRectangle(100, 100));
    setupContext();

    final var field1 = PdfField.builder()
        .id("field1")
        .value("Short text")
        .build();
    final var field2 = PdfField.builder()
        .id("field2")
        .value("Very long text that needs to be split")
        .build();

    final var fields = List.of(field1, field2);

    final var overflow = new OverFlowLineSplit("Very long text that",
        Collections.singletonList("needs to be split"));

    when(textUtil.getOverflowingLines(anyList(), eq(field1), any(PDRectangle.class), anyFloat(),
        any(PDFont.class)))
        .thenReturn(Optional.empty());
    when(textUtil.getOverflowingLines(anyList(), eq(field2), any(PDRectangle.class), anyFloat(),
        any(PDFont.class)))
        .thenReturn(Optional.of(overflow));

    final var result = pdfPaginationUtil.paginateFields(context, fields, overflowField);

    assertAll(
        () -> assertEquals(2, result.size(), "Should create 2 pages"),
        () -> assertEquals(2, result.getFirst().size(), "First page should have 2 fields"),
        () -> assertEquals(1, result.get(1).size(), "Second page should have 1 field"),
        () -> assertEquals("Very long text that", result.getFirst().get(1).getValue(),
            "Field on page 1 should be part one of split"),
        () -> assertEquals("needs to be split", result.get(1).getFirst().getValue(),
            "Field on page 2 should be part two of split")
    );
  }

  @Test
  void shouldHandleFieldSplitCorrectly() {
    setupOverflowField(new PDRectangle(100, 100));
    setupContext();

    final var field = PdfField.builder()
        .id("field1")
        .value("Very long text that needs to be split into multiple parts")
        .appearance(DEFAULT_APPEARANCE)
        .build();

    final var fields = List.of(field);

    final var overflow = new OverFlowLineSplit("Very long text that",
        Collections.singletonList("needs to be split into multiple parts"));

    when(textUtil.getOverflowingLines(anyList(), any(PdfField.class), any(PDRectangle.class),
        anyFloat(), any(PDFont.class)))
        .thenReturn(Optional.of(overflow));

    final var result = pdfPaginationUtil.paginateFields(context, fields, overflowField);

    assertAll(
        () -> assertEquals(2, result.size(), "Should create 2 pages"),
        () -> assertEquals(overflow.partOne(), result.getFirst().getFirst().getValue(),
            "First page should have part one"),
        () -> assertEquals(
            overflow.overflowPages().getFirst(),
            result.get(1).getFirst().getValue(),
            "Second page should have 1 field")
    );
  }

  @Test
  void shouldSetAppendFlagOnSecondPartOfSplitField() {
    setupOverflowField(new PDRectangle(100, 100));
    setupContext();

    final var field = PdfField.builder()
        .id("field1")
        .value("Text to split")
        .appearance(DEFAULT_APPEARANCE)
        .build();

    final var fields = List.of(field);

    final var overflow = new OverFlowLineSplit("Text", Collections.singletonList("to split"));

    when(textUtil.getOverflowingLines(anyList(), any(PdfField.class), any(PDRectangle.class),
        anyFloat(), any(PDFont.class)))
        .thenReturn(Optional.of(overflow));

    final var result = pdfPaginationUtil.paginateFields(context, fields, overflowField);

    assertAll(
        () -> assertFalse(result.getFirst().getFirst().getAppend(),
            "First part should not have append flag"),
        () -> assertTrue(result.get(1).getFirst().getAppend(),
            "Second part should have append flag")
    );
  }

  @Test
  void shouldPreserveFieldIdWhenSplitting() {
    setupOverflowField(new PDRectangle(100, 100));
    setupContext();

    final var field = PdfField.builder()
        .id("originalFieldId")
        .value("Text to split")
        .appearance(DEFAULT_APPEARANCE)
        .build();

    final var fields = List.of(field);

    final var overflow = new OverFlowLineSplit("Text", Collections.singletonList("to split"));

    when(textUtil.getOverflowingLines(anyList(), any(PdfField.class), any(PDRectangle.class),
        anyFloat(), any(PDFont.class)))
        .thenReturn(Optional.of(overflow));

    final var result = pdfPaginationUtil.paginateFields(context, fields, overflowField);

    assertAll(
        () -> assertEquals("originalFieldId", result.getFirst().getFirst().getId(),
            "First part should have original ID"),
        () -> assertEquals("originalFieldId", result.get(1).getFirst().getId(),
            "Second part should have original ID")
    );
  }

  @Test
  void shouldPreserveAppearanceWhenSplitting() {
    setupOverflowField(new PDRectangle(100, 100));
    setupContext();

    final var customAppearance = "/Helvetica 12 Tf";
    final var field = PdfField.builder()
        .id("field1")
        .value("Text to split")
        .appearance(customAppearance)
        .build();

    final var fields = List.of(field);

    final var overflow = new OverFlowLineSplit("Text", Collections.singletonList("to split"));

    when(textUtil.getOverflowingLines(anyList(), any(PdfField.class), any(PDRectangle.class),
        anyFloat(), any(PDFont.class)))
        .thenReturn(Optional.of(overflow));

    final var result = pdfPaginationUtil.paginateFields(context, fields, overflowField);

    assertEquals(customAppearance, result.get(1).getFirst().getAppearance(),
        "Appearance should be preserved on split field");
  }

  @Test
  void shouldHandleMultipleFieldsWithOverflow() {
    setupOverflowField(new PDRectangle(100, 100));
    setupContext();

    final var field1 = PdfField.builder()
        .id("field1")
        .value("First field")
        .appearance(DEFAULT_APPEARANCE)
        .build();
    final var field2 = PdfField.builder()
        .id("field2")
        .value("Second field with overflow")
        .appearance(DEFAULT_APPEARANCE)
        .build();
    final var field3 = PdfField.builder()
        .id("field3")
        .value("Third field")
        .appearance(DEFAULT_APPEARANCE)
        .build();

    final var fields = List.of(field1, field2, field3);

    final var overflow = new OverFlowLineSplit("Second field",
        Collections.singletonList("with overflow"));

    when(textUtil.getOverflowingLines(anyList(), eq(field1), any(PDRectangle.class), anyFloat(),
        any(PDFont.class)))
        .thenReturn(Optional.empty());
    when(textUtil.getOverflowingLines(anyList(), eq(field2), any(PDRectangle.class), anyFloat(),
        any(PDFont.class)))
        .thenReturn(Optional.of(overflow));
    when(textUtil.getOverflowingLines(anyList(), eq(field3), any(PDRectangle.class), anyFloat(),
        any(PDFont.class)))
        .thenReturn(Optional.empty());

    final var result = pdfPaginationUtil.paginateFields(context, fields, overflowField);

    assertAll(
        () -> assertEquals(2, result.size(), "Should create 2 pages"),
        () -> assertEquals(2, result.getFirst().size(),
            "First page should have 2 fields (field1 and part one of field2)"),
        () -> assertEquals(2, result.get(1).size(),
            "Second page should have 2 fields (part two of field2 and field3 on next page)")
    );
  }

  @Test
  void shouldHandleFieldWithOnlyPartOne() {
    setupOverflowField(new PDRectangle(100, 100));
    setupContext();

    final var field = PdfField.builder()
        .id("field1")
        .value("Text with only part one")
        .appearance(DEFAULT_APPEARANCE)
        .build();

    final var fields = List.of(field);

    final var overflow = new OverFlowLineSplit("Text with only part one", List.of());

    when(textUtil.getOverflowingLines(anyList(), any(PdfField.class), any(PDRectangle.class),
        anyFloat(), any(PDFont.class)))
        .thenReturn(Optional.of(overflow));

    final var result = pdfPaginationUtil.paginateFields(context, fields, overflowField);

    assertAll(
        () -> assertEquals(1, result.size()),
        () -> assertEquals(1, result.getFirst().size()),
        () -> assertEquals("Text with only part one", result.getFirst().getFirst().getValue())
    );
  }

  @Test
  void shouldCallTextUtilWithCorrectParameters() {
    final var rectangle = new PDRectangle(100, 100);
    setupOverflowField(rectangle);
    setupContext();

    final var field = PdfField.builder()
        .id("field1")
        .value("Test text")
        .build();

    final var fields = List.of(field);

    when(textUtil.getOverflowingLines(anyList(), any(PdfField.class), any(PDRectangle.class),
        anyFloat(), any(PDFont.class)))
        .thenReturn(Optional.empty());

    pdfPaginationUtil.paginateFields(context, fields, overflowField);

    verify(textUtil).getOverflowingLines(anyList(), eq(field), eq(rectangle), eq(FONT_SIZE),
        eq(font));
  }

  @Test
  void shouldHandleNullPartOneInOverflow() {
    setupOverflowField(new PDRectangle(100, 100));
    setupContext();

    final var field = PdfField.builder()
        .id("field1")
        .value("Text")
        .appearance(DEFAULT_APPEARANCE)
        .build();

    final var fields = List.of(field);

    final var overflow = new OverFlowLineSplit(null, Collections.singletonList("overflow part"));

    when(textUtil.getOverflowingLines(anyList(), any(PdfField.class), any(PDRectangle.class),
        anyFloat(), any(PDFont.class)))
        .thenReturn(Optional.of(overflow));

    final var result = pdfPaginationUtil.paginateFields(context, fields, overflowField);

    assertAll(
        () -> assertEquals(2, result.size(), "Should create 2 pages"),
        () -> assertEquals(1, result.getFirst().size(), "First page should have 1 field"),
        () -> assertNull(result.getFirst().getFirst(),
            "First field should be null when partOne is null"),
        () -> assertEquals(1, result.get(1).size(), "Second page should have 1 field"),
        () -> assertEquals("field1", result.get(1).getFirst().getId(),
            "Second page field should have original ID"),
        () -> assertEquals("overflow part", result.get(1).getFirst().getValue(),
            "Second page should have partTwo value")
    );
  }

  @Test
  void shouldReturnMultiplePagesWhenFieldOverflowsMultipleTimes() {
    setupOverflowField(new PDRectangle(100, 100));
    setupContext();

    final var field = PdfField.builder()
        .id("field1")
        .value("Text that overflows multiple times and needs to be split into several pages")
        .appearance(DEFAULT_APPEARANCE)
        .build();

    when(textUtil.getOverflowingLines(anyList(), any(PdfField.class), any(PDRectangle.class),
        anyFloat(), any(PDFont.class)))
        .thenReturn(Optional.of(new OverFlowLineSplit("Text that overflows multiple times",
            List.of("and needs to be split", "into several pages"))));

    final var result = pdfPaginationUtil.paginateFields(context, List.of(field), overflowField);

    assertAll(
        () -> assertEquals(3, result.size(), "Should create 3 pages"),
        () -> assertEquals("Text that overflows multiple times",
            result.getFirst().getFirst().getValue(),
            "First page should have part one"),
        () -> assertEquals("and needs to be split", result.get(1).getFirst().getValue(),
            "Second page should have part two"),
        () -> assertEquals("into several pages", result.get(2).getFirst().getValue(),
            "Third page should have part three")
    );
  }

  @Test
  void shouldThrowExceptionIfOverflowFieldIsNotVariableText() {

    when(textFieldAppearanceFactory.create(any())).thenReturn(Optional.empty());

    final List<PdfField> emptyList = List.of();

    assertThrows(IllegalStateException.class,
        () -> pdfPaginationUtil.paginateFields(context, emptyList, overflowField));
  }

  private void setupOverflowField(PDRectangle rectangle) {
    final var mockWidget = mock(PDAnnotationWidget.class);
    when(overflowField.getWidgets()).thenReturn(List.of(mockWidget));
    when(mockWidget.getRectangle()).thenReturn(rectangle);
    when(overflowField.getDefaultAppearance()).thenReturn(DEFAULT_APPEARANCE);
  }

  private void setupContext() {
    final var mockAcroForm = mock(org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm.class);
    final var mockResources = mock(org.apache.pdfbox.pdmodel.PDResources.class);

    when(context.getAcroForm()).thenReturn(mockAcroForm);
    when(mockAcroForm.getDefaultResources()).thenReturn(mockResources);

    try {
      when(mockResources.getFont(any(org.apache.pdfbox.cos.COSName.class))).thenReturn(font);
    } catch (java.io.IOException e) {
      throw new RuntimeException(e);
    }
  }
}