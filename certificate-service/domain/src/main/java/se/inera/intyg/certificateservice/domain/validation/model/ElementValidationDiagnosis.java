package se.inera.intyg.certificateservice.domain.validation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
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

  FieldId mandatoryField;
  List<FieldId> order;
  DiagnosisCodeRepository diagnosisCodeRepository;

  @Override
  public List<ValidationError> validate(ElementData data,
      Optional<ElementId> categoryId, List<ElementData> dataList) {
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
      //TODO: Look into how we can make this error message more dynamic depending on which field is mandatory
      return List.of(
          errorMessage(data, mandatoryField, categoryId, "Ange diagnos på översta raden först.")
      );
    }

    final var validationErrors = new ArrayList<ValidationError>();
    if (order != null) {
      final var elementDiagnosesMap = getElementDiagnosesMap(elementValueDiagnosisList);
      validationErrors.addAll(
          incorrectOrderValidationErrors(
              data, categoryId,
              elementDiagnosesMap
          )
      );
    }

    if (diagnosisCodeRepository != null) {
      validationErrors.addAll(
          invalidDiagnosisCodeValidationErrors(
              data,
              categoryId,
              elementValueDiagnosisList
          )
      );
    }

    return validationErrors;
  }

  private static Map<FieldId, ElementValueDiagnosis> getElementDiagnosesMap(
      ElementValueDiagnosisList elementValueDiagnosisList) {
    return elementValueDiagnosisList.diagnoses().stream()
        .collect(Collectors.toMap(ElementValueDiagnosis::id, diagnosis -> diagnosis));
  }

  private List<ValidationError> invalidDiagnosisCodeValidationErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDiagnosisList elementValueDiagnosisList) {
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

  private List<ValidationError> incorrectOrderValidationErrors(ElementData data,
      Optional<ElementId> categoryId, Map<FieldId, ElementValueDiagnosis> elementDiagnosesMap) {
    final var validationErrors = new ArrayList<ValidationError>();
    for (int index = order.size() - 1; index > 0; index--) {
      if (!elementDiagnosesMap.containsKey(order.get(index))) {
        continue;
      }

      while (index > 0 && missingDiagnosisValue(elementDiagnosesMap, index)) {
        index--;
        final var orderId = order.get(index);
        if (missingField(elementDiagnosesMap.get(orderId), ElementValueDiagnosis::code)) {
          validationErrors.add(
              errorMessage(data, orderId, categoryId, "Ange diagnoskod.")
          );
        }

        if (missingField(elementDiagnosesMap.get(orderId), ElementValueDiagnosis::description)) {
          validationErrors.add(
              errorMessage(data, new FieldId(orderId.value()), categoryId,
                  "Ange diagnosbeskrivning.")
          );
        }
      }
    }
    return validationErrors;
  }

  private static boolean missingField(
      ElementValueDiagnosis elementValueDiagnosis,
      Function<ElementValueDiagnosis, String> function) {
    return elementValueDiagnosis == null
        || function.apply(elementValueDiagnosis) == null
        || function.apply(elementValueDiagnosis).isBlank();
  }

  private boolean missingDiagnosisValue(Map<FieldId, ElementValueDiagnosis> elementDiagnosesMap,
      int index) {
    final var fieldId = order.get(index - 1);
    return !elementDiagnosesMap.containsKey(fieldId)
        || missingField(elementDiagnosesMap.get(fieldId), ElementValueDiagnosis::code)
        || missingField(elementDiagnosesMap.get(fieldId), ElementValueDiagnosis::description);
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