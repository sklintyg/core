package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

public class DiagnosisSummaryValue {

  private DiagnosisSummaryValue() {
    throw new IllegalStateException("Utility class");
  }

  public static String value(ElementId elementId, FieldId fieldId, Certificate certificate) {
    final var elementDataDiagnosis = certificate.elementData()
        .stream()
        .filter(elementData -> elementId.equals(elementData.id()))
        .findFirst();

    if (elementDataDiagnosis.isEmpty()) {
      return "";
    }

    if (!(elementDataDiagnosis.get()
        .value() instanceof ElementValueDiagnosisList elementValueDiagnosisList)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementDataDiagnosis.get().value())
      );
    }

    return elementValueDiagnosisList.diagnoses()
        .stream()
        .filter(elementValueDiagnosis -> elementValueDiagnosis.id().equals(fieldId))
        .findFirst()
        .map(ElementValueDiagnosis::description)
        .orElse("");
  }
}
