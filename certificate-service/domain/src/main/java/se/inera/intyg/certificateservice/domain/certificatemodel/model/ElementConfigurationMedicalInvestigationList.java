package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;

@Value
@Builder
public class ElementConfigurationMedicalInvestigationList implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.MEDICAL_INVESTIGATION;
  @Getter(onMethod = @__(@Override))
  String description;
  @Getter(onMethod = @__(@Override))
  String header;
  @Getter(onMethod = @__(@Override))
  String label;
  FieldId id;
  String typeText;
  String dateText;
  String informationSourceText;
  String informationSourceDescription;
  List<MedicalInvestigationConfig> list;

  @Override
  public ElementValue emptyValue() {
    return ElementValueText.builder()
        .textId(id)
        .build();
  }
}
