package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//@Value
@Getter
@Setter
@Builder
public class PdfField {

  String id;
  String value;
  @Builder.Default
  Boolean append = false;
  String appearance;
}
