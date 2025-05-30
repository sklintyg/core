package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfConfigurationBoolean implements PdfConfiguration {

  PdfFieldId checkboxTrue;
  PdfFieldId checkboxFalse;
  boolean isRadioButton;
  String valueTrue;
  String valueFalse;
}
