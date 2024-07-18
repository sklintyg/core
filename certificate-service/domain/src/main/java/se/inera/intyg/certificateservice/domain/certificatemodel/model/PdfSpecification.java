package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfSpecification {

  CertificateType certificateType;
  Mcid mcid;
  PdfTagIndex signatureWithAddressTagIndex;
  PdfTagIndex signatureWithoutAddressTagIndex;
  PdfFieldId patientIdFieldId;
  List<PdfQuestionField> pdfQuestionFields;
}
