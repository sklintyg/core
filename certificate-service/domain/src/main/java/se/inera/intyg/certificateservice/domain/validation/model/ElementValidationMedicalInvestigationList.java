package se.inera.intyg.certificateservice.domain.validation.model;

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
  public List<ValidationError> validate(ElementData data, Optional<ElementId> categoryId,
      List<ElementData> dataList) {
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
      return Boolean.TRUE.equals(isIncomplete(medicalInvestigationList.list().get(0)))
          ? getRowEmptyFieldErrors(
          medicalInvestigationList.list().get(0), data, categoryId) : Collections.emptyList();
    }

    if (isEmpty(medicalInvestigationList.list().get(0)) || isIncomplete(
        medicalInvestigationList.list().get(0))) {
      return Stream.concat(Stream.of(errorMessage(
              data,
              medicalInvestigationList.list().get(0).id(),
              categoryId,
              ErrorMessageFactory.missingAnswer())),
          getRowEmptyFieldErrors(medicalInvestigationList.list().get(0), data, categoryId).stream()
      ).toList();
    }

    return Collections.emptyList();
  }


  private List<ValidationError> getDateAfterMaxErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    return medicalInvestigationList.list().stream()
        .filter(medicalInvestigation -> ElementValidator.isDateAfterMax(
                medicalInvestigation.date().date(),
                max
            )
        )
        .map(medicalInvestigation -> errorMessage(
            data,
            medicalInvestigation.date().dateId(),
            categoryId,
            ErrorMessageFactory.maxDate(max)
        ))
        .toList();
  }

  private List<ValidationError> getDateBeforeMinErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    return medicalInvestigationList.list().stream()
        .filter(medicalInvestigation -> ElementValidator.isDateBeforeMin(
                medicalInvestigation.date().date(),
                min
            )
        )
        .map(medicalInvestigation -> errorMessage(
            data,
            medicalInvestigation.date().dateId(),
            categoryId,
            ErrorMessageFactory.minDate(min)
        ))
        .toList();
  }

  private List<ValidationError> getTextLimitErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    return medicalInvestigationList.list().stream()
        .filter(medicalInvestigation -> ElementValidator.isTextOverLimit(
                medicalInvestigation.informationSource().text(),
                limit
            )
        )
        .map(medicalInvestigation -> errorMessage(
            data,
            medicalInvestigation.informationSource().textId(),
            categoryId,
            ErrorMessageFactory.textLimit(limit)
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
          ErrorMessageFactory.fieldOrderDescending()
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

    if (isDateEmpty(medicalInvestigation)) {
      errors.add(errorMessage(
          data,
          medicalInvestigation.date().dateId(),
          categoryId,
          ErrorMessageFactory.missingDate()));
    }

    if (isTypeEmpty(medicalInvestigation)) {
      errors.add(errorMessage(
          data,
          medicalInvestigation.investigationType().codeId(),
          categoryId,
          ErrorMessageFactory.missingOption()));
    }

    if (isSourceEmpty(medicalInvestigation)) {
      errors.add(errorMessage(
          data,
          medicalInvestigation.informationSource().textId(),
          categoryId,
          ErrorMessageFactory.missingAnswer()));
    }
    return errors;
  }

  private Boolean hasIncompleteOrEmptyRow(List<MedicalInvestigation> medicalInvestigationList) {
    return medicalInvestigationList.stream()
        .anyMatch(row -> isIncomplete(row) || isEmpty(row));
  }

  private Boolean isIncomplete(MedicalInvestigation medicalInvestigation) {
    return !isEmpty(medicalInvestigation)
        && (isDateEmpty(medicalInvestigation)
        || isTypeEmpty(medicalInvestigation)
        || isSourceEmpty(medicalInvestigation));
  }

  private Boolean isEmpty(MedicalInvestigation medicalInvestigation) {
    if (medicalInvestigation == null) {
      return true;
    }
    return isDateEmpty(medicalInvestigation)
        && isTypeEmpty(medicalInvestigation)
        && isSourceEmpty(medicalInvestigation);
  }

  private static boolean isSourceEmpty(MedicalInvestigation medicalInvestigation) {
    return !ElementValidator.isTextDefined(medicalInvestigation.informationSource().text());
  }

  private static boolean isTypeEmpty(MedicalInvestigation medicalInvestigation) {
    return !ElementValidator.isTextDefined(medicalInvestigation.investigationType().code());
  }

  private static boolean isDateEmpty(MedicalInvestigation medicalInvestigation) {
    return medicalInvestigation.date().date() == null;
  }

  private ElementValueMedicalInvestigationList getValue(ElementValue value) {
    if (value instanceof ElementValueMedicalInvestigationList medicalInvestigationList) {
      return medicalInvestigationList;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );
  }

  private ValidationError errorMessage(ElementData data,
      FieldId fieldId,
      Optional<ElementId> categoryId, ErrorMessage message) {
    return
        ValidationError.builder()
            .elementId(data.id())
            .fieldId(fieldId)
            .categoryId(categoryId.orElse(null))
            .message(message)
            .build();
  }

  private void validateElementData(ElementData data) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }
    if (data.value() == null) {
      throw new IllegalArgumentException("Element data value is null");
    }
  }

  private boolean isMedicalInvestigationNotInitialized(
      MedicalInvestigation medicalInvestigation) {
    return medicalInvestigation.date() == null
        || medicalInvestigation.investigationType() == null
        || medicalInvestigation.informationSource() == null;
  }

}