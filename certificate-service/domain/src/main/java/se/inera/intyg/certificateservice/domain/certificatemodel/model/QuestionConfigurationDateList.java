package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QuestionConfigurationDateList implements QuestionConfiguration {

  FieldId questionFieldId;
  PdfFieldId checkboxFieldId;
  PdfFieldId dateFieldId;

}
