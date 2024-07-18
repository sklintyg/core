package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfQuestionField {

  ElementId questionId;
  PdfFieldId pdfFieldId;
  PdfValueType pdfValueType;
}
