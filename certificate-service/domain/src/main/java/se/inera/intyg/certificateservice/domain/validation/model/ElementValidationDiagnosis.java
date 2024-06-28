package se.inera.intyg.certificateservice.domain.validation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationDiagnosis implements ElementValidation {

  private static final String DIAGNOSIS_DESCRIPTION = ".description";
  FieldId mandatoryField;
  List<FieldId> order;
  DiagnosisCodeRepository diagnosisCodeRepository;

  @Override
  public List<ValidationError> validate(ElementData data,
      Optional<ElementId> categoryId) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }

    final var elementValueDiagnosisList = getValue(data.value());

    if (mandatoryField != null && elementValueDiagnosisList.diagnoses().isEmpty()) {
      return List.of(
          errorMessage(data, mandatoryField, categoryId, "Ange minst en diagnos.")
      );
    }

    if (mandatoryField != null && missingMandatoryField(elementValueDiagnosisList)) {
      return List.of(
          errorMessage(data, mandatoryField, categoryId, "Ange diagnos på översta raden först.")
      );
    }

    if (order != null) {
      final var validationErrors = new ArrayList<ValidationError>();
      final var elementDiagnoseIds = elementValueDiagnosisList.diagnoses().stream()
          .map(ElementValueDiagnosis::id)
          .toList();

      for (int index = order.size() - 1; index > 0; index--) {
        if (elementDiagnoseIds.contains(order.get(index))) {
          while (index > 0 && !elementDiagnoseIds.contains(order.get(index - 1))) {
            index--;
            final var orderId = order.get(index);
            validationErrors.add(
                errorMessage(data, orderId, categoryId, "Ange diagnoskod.")
            );
            validationErrors.add(
                errorMessage(data, new FieldId(orderId.value() + DIAGNOSIS_DESCRIPTION), categoryId,
                    "Ange diagnosbeskrivning.")
            );
          }
        }
      }
      if (!validationErrors.isEmpty()) {
        return validationErrors;
      }
    }

    if (diagnosisCodeRepository != null) {
      final var validationErrors = new ArrayList<ValidationError>();
      elementValueDiagnosisList.diagnoses().forEach(elementValueDiagnosis -> {
        final var code = elementValueDiagnosis.code();
        if (codeIsInvalid(new DiagnosisCode(code))) {
          validationErrors.add(
              errorMessage(data, elementValueDiagnosis.id(), categoryId,
                  "Diagnoskod är ej giltig.")
          );
        }
      });
      return validationErrors;
    }

    return Collections.emptyList();
  }

  private boolean codeIsInvalid(DiagnosisCode diagnosisCode) {
    return diagnosisCodeRepository.findByCode(diagnosisCode).isEmpty();
  }

  private boolean missingMandatoryField(ElementValueDiagnosisList elementValueDiagnosisList) {
    return elementValueDiagnosisList.diagnoses().stream()
        .noneMatch(elementValueDiagnosis -> elementValueDiagnosis.id().equals(mandatoryField));
  }

  private ElementValueDiagnosisList getValue(ElementValue value) {
    if (value == null) {
      throw new IllegalArgumentException("Element data value is null");
    }

    if (value instanceof ElementValueDiagnosisList valueDiagnosisList) {
      return valueDiagnosisList;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );
  }

  private static ValidationError errorMessage(
      ElementData data,
      FieldId fieldId,
      Optional<ElementId> categoryId,
      String message) {
    return ValidationError.builder()
        .elementId(data.id())
        .fieldId(fieldId)
        .categoryId(categoryId.orElse(null))
        .message(new ErrorMessage(message))
        .build();
  }
}
