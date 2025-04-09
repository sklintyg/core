package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfConfigurationRadioBoolean implements PdfConfiguration {

  PdfFieldId pdfFieldId;
  PdfRadioOption optionTrue;
  PdfRadioOption optionFalse;
}
