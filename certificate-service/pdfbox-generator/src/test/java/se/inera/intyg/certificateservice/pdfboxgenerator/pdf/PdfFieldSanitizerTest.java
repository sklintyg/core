package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.junit.jupiter.api.Test;

class PdfFieldSanitizerTest {

  @Test
  void shouldSanitizePdfFieldsCorrectly() {
    var sanitizer = new PdfFieldSanitizer();

    var field = PdfField.builder()
        .value("text\u2013with\u2014dashes")
        .build();

    PDFont mockFont = mock(PDFont.class);

    sanitizer.sanitize(field, mockFont);

    assertEquals("text-with-dashes", field.getValue());
  }
}