package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextUtil;

@Getter
@Builder
@EqualsAndHashCode
public class PdfField {

  String id;
  @Setter
  String value;
  @Builder.Default
  Boolean append = false;
  String appearance;
  Integer offset;

  public String sanitizedValue() {
    if (this.value == null || this.value.isEmpty()) {
      return "";
    }
    
    return TextUtil.normalizePrintableCharacters(value);
  }
}