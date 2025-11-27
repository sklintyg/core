package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueTable;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;

@Value
@Builder
public class ElementConfigurationDiagnosis implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.DIAGNOSIS;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  @Getter(onMethod = @__(@Override))
  String description;
  FieldId id;
  @Builder.Default
  List<ElementDiagnosisTerminology> terminology = Collections.emptyList();
  @Builder.Default
  List<ElementDiagnosisListItem> list = Collections.emptyList();

  @Override
  public ElementValue emptyValue() {
    return ElementValueDiagnosisList.builder()
        .diagnoses(Collections.emptyList())
        .build();
  }

  public String codeSystem(String id) {
    return this.terminology.stream()
        .filter(elementDiagnosisTerminology -> elementDiagnosisTerminology.id().equals(id))
        .findFirst()
        .map(ElementDiagnosisTerminology::codeSystem)
        .orElseThrow(() -> new IllegalStateException("No code system found for id: " + id));
  }

  @Override
  public Optional<ElementSimplifiedValue> simplified(ElementValue value) {
    if (!(value instanceof ElementValueDiagnosisList elementValue)) {
      throw new IllegalStateException("Wrong value type");
    }

    if (elementValue.isEmpty()) {
      return Optional.of(ElementSimplifiedValueText.builder()
          .text("Ej angivet")
          .build());
    }

    return Optional.of(
        ElementSimplifiedValueTable.builder()
            .headings(List.of("Diagnoskod enligt ICD-10 SE", ""))
            .values(elementValue.diagnoses().stream()
                .map(diagnosis -> List.of(
                    diagnosis.code() != null ? diagnosis.code() : "",
                    diagnosis.description() != null ? diagnosis.description() : ""
                ))
                .toList())
            .build()
    );
  }
}
