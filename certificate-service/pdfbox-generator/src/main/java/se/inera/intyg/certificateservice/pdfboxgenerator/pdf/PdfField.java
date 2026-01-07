package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.apache.pdfbox.pdmodel.font.PDFont;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextUtil;

@Value
@Builder
public class PdfField {

  String id;
  @With
  String value;
  @Builder.Default
  Boolean append = false;
  @Builder.Default
  Boolean patientField = false;
  @Builder.Default
  Boolean unitField = false;
  @With
  String appearance;
  @Builder.Default
  Integer offset = 0;

  public String sanitizedValue(PDFont font) {
    if (this.value == null || this.value.isEmpty()) {
      return "";
    }

    return TextUtil.sanitizeText(value, font);
  }

  public String normalizedValue(PDFont font) {
    if (this.value == null || this.value.isEmpty()) {
      return "";
    }

    return TextUtil.normalizePrintableCharacters(value, font);
  }

  public boolean isPatientField() {
    return Boolean.TRUE.equals(patientField);
  }
}