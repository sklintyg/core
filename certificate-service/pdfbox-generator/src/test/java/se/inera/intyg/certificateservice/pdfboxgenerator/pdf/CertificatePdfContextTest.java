package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.junit.jupiter.api.Test;

class CertificatePdfContextTest {

  @Test
  void shouldIncrementMcidCorrectly() {
    final var context = CertificatePdfContext.builder()
        .mcid(new AtomicInteger(0))
        .document(new PDDocument())
        .additionalInfoText("")
        .citizenFormat(false)
        .build();

    assertAll(
        () -> assertEquals(1, context.nextMcid()),
        () -> assertEquals(2, context.nextMcid())
    );
  }

  @Test
  void shouldReturnPdfFieldsThatMatchPredicate() {
    final var expectedField = PdfField.builder()
        .id("field1")
        .patientField(true)
        .build();

    final var context = CertificatePdfContext.builder()
        .pdfFields(List.of(expectedField))
        .build();

    final var matchingFields = context.getPdfFields(PdfField::isPatientField);

    assertEquals(expectedField, matchingFields.getFirst());
  }

  @Test
  void shouldReturnEmptyWhenNoPdfFieldsMatchPredicate() {
    final var context = CertificatePdfContext.builder()
        .pdfFields(List.of())
        .build();

    final var matchingFields = context.getPdfFields(PdfField::isPatientField);

    assertEquals(0, matchingFields.size());
  }

  @Test
  void shouldSanatizePdfFields() {
    final var fieldWithProblemChars = PdfField.builder()
        .id("field1")
        .value("testing\u2013with\u2014problem\u2212characters")
        .build();

    final var fieldWithValue = PdfField.builder()
        .id("field2")
        .value("Some Value")
        .build();

    final var originalFields = List.of(fieldWithProblemChars, fieldWithValue);
    final var expectedFields = List.of(
        PdfField.builder()
            .id("field1")
            .value("testing-with-problem-characters")
            .build(),
        fieldWithValue
    );

    final var fontResolverMock = mock(PdfFontResolver.class);

    final var context = CertificatePdfContext.builder()
        .fontResolver(fontResolverMock)
        .fieldSanitizer(new PdfFieldSanitizer())
        .build();

    when(fontResolverMock.resolveFont(any())).thenReturn(new PDType1Font(FontName.HELVETICA));

    final var sanitizedFields = context.sanitizePdfFields(originalFields);

    assertEquals(expectedFields, sanitizedFields);
  }
}