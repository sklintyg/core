package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfConfigurationDateCheckbox implements PdfConfiguration {

  PdfFieldId checkboxFieldId;
  PdfFieldId dateFieldId;
}
