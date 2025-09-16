package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfConfigurationText implements PdfConfiguration {

  PdfFieldId pdfFieldId;
  Integer maxLength;
  PdfFieldId overflowSheetFieldId;
  @Builder.Default
  Integer offset = 0;
}
