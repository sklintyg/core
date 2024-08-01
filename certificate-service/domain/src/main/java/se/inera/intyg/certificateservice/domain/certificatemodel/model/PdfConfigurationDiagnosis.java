package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfConfigurationDiagnosis implements PdfConfiguration {

  PdfFieldId pdfNameFieldId;
  List<PdfFieldId> pdfCodeFieldIds;
}
