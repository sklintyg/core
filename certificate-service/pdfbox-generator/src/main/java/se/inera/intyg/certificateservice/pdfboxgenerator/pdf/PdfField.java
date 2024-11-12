package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PdfField {

  String id;
  @Setter
  String value;
  @Builder.Default
  Boolean append = false;
  String appearance;
}
