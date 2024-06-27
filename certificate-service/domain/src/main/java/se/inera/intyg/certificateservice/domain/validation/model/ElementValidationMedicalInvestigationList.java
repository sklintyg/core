package se.inera.intyg.certificateservice.domain.validation.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationMedicalInvestigationList implements ElementValidation {

  boolean mandatory;
  TemporalAmount max;
  TemporalAmount min;
  Integer limit;

  @Override
  public List<ValidationError> validate(ElementData data, Optional<ElementId> categoryId) {
    validateElementData(data);
    final var medicalInvestigationList = getValue(data.value());

    if (medicalInvestigationList.list().isEmpty()
        || medicalInvestigationList.list().stream()
        .anyMatch(this::isMedicalInvestigationNotInitialized)) {
      return Collections.emptyList();
    }

    final var dateAfterMaxErrors = getDateAfterMaxErrors(data, categoryId,
        medicalInvestigationList);
    final var dateBeforeMinErrors = getDateBeforeMinErrors(data, categoryId,
        medicalInvestigationList);
    final var mandatoryErrors = getMandatoryErrors(data, categoryId, medicalInvestigationList);
    final var rowOrderErrors = getRowOrderErrors(data, categoryId, medicalInvestigationList);
    final var textLimitErrors = getTextLimitErrors(data, categoryId, medicalInvestigationList);

    return Stream.of(dateAfterMaxErrors, dateBeforeMinErrors, mandatoryErrors, rowOrderErrors,
            textLimitErrors)
        .flatMap(List::stream)
        .toList();
  }

  private List<ValidationError> getMandatoryErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    if (!mandatory) {
      return Collections.emptyList();
    }

    if (hasIncorrectRowOrder(medicalInvestigationList)) {
      return Collections.emptyList();
    }

    if (isEmpty(medicalInvestigationList.list().get(0)) || isIncomplete(
        medicalInvestigationList.list().get(0))) {
      return Stream.concat(Stream.of(errorMessage(
              data,
              medicalInvestigationList.list().get(0).id(),
              categoryId,
              "Ange ett svar.")),
          getRowEmptyFieldErrors(medicalInvestigationList.list().get(0), data, categoryId).stream()
      ).toList();
    }

    return Collections.emptyList();
  }


  private List<ValidationError> getDateAfterMaxErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    return medicalInvestigationList.list().stream()
        .filter(medicalInvestigation -> isAfterMax(medicalInvestigation.date().date()))
        .map(medicalInvestigation -> errorMessage(
            data,
            medicalInvestigation.date().dateId(),
            categoryId,
            "Ange ett datum som är senast %s.".formatted(maxDate())
        ))
        .toList();
  }

  private List<ValidationError> getDateBeforeMinErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    return medicalInvestigationList.list().stream()
        .filter(medicalInvestigation -> isDateBeforeMin(medicalInvestigation.date()))
        .map(medicalInvestigation -> errorMessage(
            data,
            medicalInvestigation.date().dateId(),
            categoryId,
            "Ange ett datum som är tidigast %s.".formatted(minDate())
        ))
        .toList();
  }

  private List<ValidationError> getTextLimitErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    return medicalInvestigationList.list().stream()
        .filter(medicalInvestigation -> isTextOverLimit(
            medicalInvestigation.informationSource().text()))
        .map(medicalInvestigation -> errorMessage(
            data,
            medicalInvestigation.informationSource().textId(),
            categoryId,
            "Ange en text som inte är längre än %s.".formatted(limit)
        ))
        .toList();
  }

  private List<ValidationError> getRowOrderErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    final var errors = new ArrayList<ValidationError>();

    final var hasIncorrectRowOrder = hasIncorrectRowOrder(medicalInvestigationList);

    if (hasIncorrectRowOrder) {
      errors.add(errorMessage(
          data,
          medicalInvestigationList.id(),
          categoryId,
          "Fyll i fälten uppifrån och ner."
      ));
    }

    final var wrongRowOrderErrors = medicalInvestigationList.list().stream()
        .filter(row -> (isEmpty(row) && hasFilledInRowBelow(medicalInvestigationList, row))
            || (isIncomplete(row) && medicalInvestigationList.list().indexOf(row) != 0))
        .map(medicalInvestigation -> getRowEmptyFieldErrors(medicalInvestigation, data, categoryId))
        .toList();

    return Stream.concat(errors.stream(), wrongRowOrderErrors.stream().flatMap(List::stream))
        .toList();
  }

  private boolean hasIncorrectRowOrder(
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    return medicalInvestigationList.list().stream()
        .anyMatch(medicalInvestigation -> isAddedInWrongOrder(medicalInvestigationList,
            medicalInvestigation));
  }

  private boolean isAddedInWrongOrder(ElementValueMedicalInvestigationList medicalInvestigationList,
      MedicalInvestigation medicalInvestigation) {
    final var listAboveObject = medicalInvestigationList.list()
        .subList(0, medicalInvestigationList.list().indexOf(medicalInvestigation));
    return !isEmpty(medicalInvestigation) && hasIncompleteOrEmptyRow(listAboveObject);
  }

  private boolean hasFilledInRowBelow(ElementValueMedicalInvestigationList medicalInvestigationList,
      MedicalInvestigation medicalInvestigation) {
    final var listUnderObject = medicalInvestigationList.list()
        .subList(medicalInvestigationList.list().indexOf(medicalInvestigation) + 1,
            medicalInvestigationList.list().size());
    return listUnderObject.stream().anyMatch(row -> !isEmpty(row));
  }

  private List<ValidationError> getRowEmptyFieldErrors(MedicalInvestigation medicalInvestigation,
      ElementData data, Optional<ElementId> categoryId) {
    final var errors = new ArrayList<ValidationError>();

    if (medicalInvestigation.date().date() == null) {
      errors.add(errorMessage(
          data,
          medicalInvestigation.date().dateId(),
          categoryId,
          "Ange ett datum."));
    }

    if (medicalInvestigation.investigationType().code() == null) {
      errors.add(errorMessage(
          data,
          medicalInvestigation.investigationType().codeId(),
          categoryId,
          "Välj ett alternativ."));
    }

    if (medicalInvestigation.informationSource().text() == null) {
      errors.add(errorMessage(
          data,
          medicalInvestigation.informationSource().textId(),
          categoryId,
          "Ange ett svar."));
    }
    return errors;
  }

  private Boolean hasIncompleteOrEmptyRow(List<MedicalInvestigation> medicalInvestigationList) {
    return medicalInvestigationList.stream()
        .anyMatch(row -> isIncomplete(row) || isEmpty(row));
  }

  private Boolean isIncomplete(MedicalInvestigation medicalInvestigation) {
    return !isEmpty(medicalInvestigation) && (medicalInvestigation.date().date() == null
        || medicalInvestigation.investigationType().code() == null
        || medicalInvestigation.informationSource().text() == null);
  }

  private Boolean isEmpty(MedicalInvestigation medicalInvestigation) {
    if (medicalInvestigation == null) {
      return true;
    }
    return medicalInvestigation.date().date() == null
        && medicalInvestigation.investigationType().code() == null
        && medicalInvestigation.informationSource().text() == null;
  }

  private ElementValueMedicalInvestigationList getValue(ElementValue value) {
    if (value instanceof ElementValueMedicalInvestigationList medicalInvestigationList) {
      return medicalInvestigationList;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );
  }

  private boolean isAfterMax(LocalDate value) {
    return value != null && max != null && value.isAfter(maxDate());
  }

  private boolean isDateBeforeMin(ElementValueDate dateValue) {
    return dateValue.date() != null && min != null && dateValue.date().isBefore(minDate());
  }

  private ValidationError errorMessage(ElementData data,
      FieldId fieldId,
      Optional<ElementId> categoryId, String message) {
    return
        ValidationError.builder()
            .elementId(data.id())
            .fieldId(fieldId)
            .categoryId(categoryId.orElse(null))
            .message(new ErrorMessage(message))
            .build();
  }

  private LocalDate minDate() {
    return LocalDate.now(ZoneId.systemDefault()).plus(min);
  }

  private LocalDate maxDate() {
    return LocalDate.now(ZoneId.systemDefault()).plus(max);
  }

  private void validateElementData(ElementData data) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }
    if (data.value() == null) {
      throw new IllegalArgumentException("Element data value is null");
    }
  }

  private boolean isTextOverLimit(String value) {
    return limit != null && value != null && value.length() > limit;
  }

  private boolean isMedicalInvestigationNotInitialized(
      MedicalInvestigation medicalInvestigation) {
    return medicalInvestigation.date() == null
        || medicalInvestigation.investigationType() == null
        || medicalInvestigation.informationSource() == null;
  }

}
