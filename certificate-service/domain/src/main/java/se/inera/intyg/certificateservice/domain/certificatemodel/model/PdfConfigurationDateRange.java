package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfConfigurationDateRange implements PdfConfiguration {

  PdfFieldId from;
  PdfFieldId to;
}
