package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.junit.jupiter.api.Test;

class PdfFieldSanitizerTest {

  @Test
  void shouldSanitizePdfFieldsCorrectly() {
    var sanitizer = new PdfFieldSanitizer();

    final var originalField = PdfField.builder()
        .value("text\u2013with\u2014dashes")
        .build();

    final var sanatizedField = PdfField.builder()
        .value("text-with-dashes")
        .build();

    PDFont mockFont = mock(PDFont.class);

    final var actual = sanitizer.sanitize(originalField, mockFont);

    assertEquals(sanatizedField, actual);
  }
}