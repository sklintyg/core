package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfSpecification {

  String pdfTemplatePath;
  String pdfNoAddressTemplatePath;
  String certificateName;
  PdfMcid pdfMcid;
  PdfTagIndex signatureWithAddressTagIndex;
  PdfTagIndex signatureWithoutAddressTagIndex;
  PdfFieldId patientIdFieldId;
  PdfFieldId overflowSheetFieldId;
  PdfSignature signature;
}
