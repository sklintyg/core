package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class TextSplitRenderSpec {

  String informationMessage;
  String fieldText;
  Integer limit;

  @Builder.Default
  boolean shouldRemoveLineBreaks = true;
}
