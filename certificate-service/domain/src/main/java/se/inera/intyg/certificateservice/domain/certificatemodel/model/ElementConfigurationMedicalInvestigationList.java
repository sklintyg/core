package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;

@Value
@Builder
public class ElementConfigurationMedicalInvestigationList implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.MEDICAL_INVESTIGATION_LIST;
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
    return ElementValueMedicalInvestigationList.builder()
        .id(id)
        .list(
            list.stream()
                .map(config -> MedicalInvestigation.builder()
                    .id(config.id())
                    .date(ElementValueDate.builder()
                        .dateId(config.dateId())
                        .build())
                    .investigationType(ElementValueCode.builder()
                        .codeId(config.investigationTypeId())
                        .build())
                    .informationSource(ElementValueText.builder()
                        .textId(config.informationSourceId())
                        .build())
                    .build()
                )
                .toList()
        )
        .build();
  }
}
