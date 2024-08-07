package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Map;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfConfigurationMedicalInvestigation implements PdfConfiguration {

  PdfFieldId datePdfFieldId;
  Map<String, String> investigationPdfOptions;
  PdfFieldId investigationPdfFieldId;
  PdfFieldId sourceTypePdfFieldId;
}
