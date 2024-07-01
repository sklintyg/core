package se.inera.intyg.certificateservice.domain.certificate.model;


import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;


@Value
@Builder
public class ElementValueDiagnosis implements ElementValue {

  FieldId id;
  String terminology;
  String code;
  String description;

}
