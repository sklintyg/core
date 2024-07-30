package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QuestionConfigurationDiagnose implements QuestionConfiguration {

  FieldId questionId;
  PdfFieldId diagnoseNameFieldId;
  List<PdfFieldId> diagnoseCodeFieldIds;

}
