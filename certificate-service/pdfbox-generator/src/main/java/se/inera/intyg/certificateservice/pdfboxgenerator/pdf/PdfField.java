package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfField {

  String id;
  String value;
}
