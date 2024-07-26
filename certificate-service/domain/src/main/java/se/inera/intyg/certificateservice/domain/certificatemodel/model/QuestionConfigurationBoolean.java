package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QuestionConfigurationBoolean implements QuestionConfiguration {

  FieldId questionId;
  PdfFieldId checkboxTrue;
  PdfFieldId checkboxFalse;
}
