package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

/**
 * PDF specification for template-based PDF generation. Uses a predefined PDF template and fills in
 * form fields.
 */
@Value
@Builder
public class TemplatePdfSpecification implements PdfSpecification {

  String pdfTemplatePath;
  String pdfNoAddressTemplatePath;
  String certificateName;
  PdfMcid pdfMcid;
  PdfTagIndex signatureWithAddressTagIndex;
  PdfTagIndex signatureWithoutAddressTagIndex;
  List<PdfFieldId> patientIdFieldIds;
  PdfSignature signature;
  OverflowPageIndex overFlowPageIndex;
  List<String> untaggedWatermarks;
  @Default
  boolean hasPageNbr = true;
}

