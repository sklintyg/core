package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class PdfFieldSanitizer {

  public PdfField sanitize(PdfField field, PDFont font) {
    if (font == null) {
      throw new IllegalArgumentException("Font must not be null");
    }

    if (field.getAppend() || field.getUnitField()) {
      return field.withValue(field.normalizedValue(font));
    }
    return field.withValue(field.sanitizedValue(font));
  }
}
