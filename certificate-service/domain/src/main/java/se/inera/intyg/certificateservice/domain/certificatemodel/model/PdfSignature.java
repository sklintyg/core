package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfSignature {

  PdfFieldId signedDateFieldId;
  PdfFieldId signedByNameFieldId;
  PdfFieldId paTitleFieldId;
  PdfFieldId specialtyFieldId;
  PdfFieldId hsaIdFieldId;
  PdfFieldId workplaceCodeFieldId;
  PdfFieldId contactInformation;
}
