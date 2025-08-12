package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfConfigurationDateRangeCheckbox implements PdfConfiguration {

  PdfFieldId from;
  PdfFieldId to;
  PdfFieldId checkbox;
}
