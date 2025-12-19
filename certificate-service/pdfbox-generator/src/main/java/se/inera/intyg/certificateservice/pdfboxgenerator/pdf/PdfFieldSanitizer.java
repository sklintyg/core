package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class PdfFieldSanitizer {

  public void sanitize(PdfField field, PDFont font) {
    if (font == null) {
      throw new IllegalArgumentException("Font must not be null");
    }

    if (field.getAppend() || field.getUnitField()) {
      field.setValue(field.normalizedValue(font));
    } else {
      field.setValue(field.sanitizedValue(font));
    }
  }
}
