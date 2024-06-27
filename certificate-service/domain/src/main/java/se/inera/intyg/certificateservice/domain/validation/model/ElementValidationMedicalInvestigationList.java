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

  // TODO: Validate on text limit and validate on code matching code system

  boolean mandatory;
  TemporalAmount max;
  TemporalAmount min;

  @Override
  public List<ValidationError> validate(ElementData data, Optional<ElementId> categoryId) {
    validateElementData(data);
    final var medicalInvestigationList = getValue(data.value());
    final var dateAfterMaxErrors = getDateAfterMaxErrors(data, categoryId,
        medicalInvestigationList);
    final var dateBeforeMinErrors = getDateBeforeMinErrors(data, categoryId,
        medicalInvestigationList);
    final var mandatoryErrors = getMandatoryErrors(data, categoryId, medicalInvestigationList);
    final var dateIncompleteErrors = getDateIncompleteErrors(data, categoryId,
        medicalInvestigationList);
    final var investigationTypeIncompleteErrors = getInvestigationTypeIncompleteErrors(data,
        categoryId, medicalInvestigationList);
    final var informationSourceIncompleteErrors = getInformationSourceIncompleteErrors(data,
        categoryId, medicalInvestigationList);
    final var rowOrderErrors = getRowOrderErrors(data, categoryId, medicalInvestigationList);

    return Stream.of(dateAfterMaxErrors, dateBeforeMinErrors, mandatoryErrors, dateIncompleteErrors,
            investigationTypeIncompleteErrors, informationSourceIncompleteErrors, rowOrderErrors)
        .flatMap(List::stream)
        .toList();
  }

  private List<ValidationError> getMandatoryErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    if (mandatory && Boolean.TRUE.equals(isEmpty(medicalInvestigationList.list().get(0)))) {
      return List.of(errorMessage(
          data,
          medicalInvestigationList.list().get(0).id(),
          categoryId,
          "Ange ett svar."
      ));
    }

    return Collections.emptyList();
  }

  private void validateElementData(ElementData data) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }
    if (data.value() == null) {
      throw new IllegalArgumentException("Element data value is null");
    }
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

  private List<ValidationError> getDateIncompleteErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    return medicalInvestigationList.list().stream()
        .filter(medicalInvestigation -> !isEmpty(medicalInvestigation)
            && medicalInvestigation.date().date() == null)
        .map(medicalInvestigation -> errorMessage(
            data,
            medicalInvestigation.date().dateId(),
            categoryId,
            "Ange ett datum."
        ))
        .toList();
  }

  private List<ValidationError> getInvestigationTypeIncompleteErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    return medicalInvestigationList.list().stream()
        .filter(medicalInvestigation -> !isEmpty(medicalInvestigation)
            && medicalInvestigation.investigationType().code() == null)
        .map(medicalInvestigation -> errorMessage(
            data,
            medicalInvestigation.investigationType().codeId(),
            categoryId,
            "Välj ett alternativ."
        ))
        .toList();
  }

  private List<ValidationError> getInformationSourceIncompleteErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    return medicalInvestigationList.list().stream()
        .filter(medicalInvestigation -> !isEmpty(medicalInvestigation)
            && medicalInvestigation.informationSource().text() == null)
        .map(medicalInvestigation -> errorMessage(
            data,
            medicalInvestigation.informationSource().textId(),
            categoryId,
            "Ange ett svar."
        ))
        .toList();
  }

  private List<ValidationError> getRowOrderErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueMedicalInvestigationList medicalInvestigationList) {
    final var errors = new ArrayList<ValidationError>();

    final var hasIncorrectRowOrder = medicalInvestigationList.list().stream()
        .anyMatch(medicalInvestigation -> !isEmpty(medicalInvestigation)
            && hasIncompleteRow(medicalInvestigationList.list()
            .subList(0, medicalInvestigationList.list().indexOf(medicalInvestigation))));

    if (hasIncorrectRowOrder) {
      errors.add(errorMessage(
          data,
          medicalInvestigationList.id(),
          categoryId,
          "Fyll i fälten uppifrån och ned."
      ));
    }
    final var emptyFieldErrors = medicalInvestigationList.list().stream()
        .filter(this::isIncomplete)
        .map(medicalInvestigation -> getRowEmptyFieldErrors(medicalInvestigation, data, categoryId))
        .toList();

    return Stream.concat(errors.stream(), emptyFieldErrors.stream().flatMap(List::stream))
        .toList();
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

  private Boolean isIncomplete(MedicalInvestigation medicalInvestigation) {
    return medicalInvestigation.date().date() == null
        || medicalInvestigation.investigationType().code() == null
        || medicalInvestigation.informationSource().text() == null;
  }

  private Boolean hasIncompleteRow(List<MedicalInvestigation> medicalInvestigationList) {
    return medicalInvestigationList.stream()
        .anyMatch(this::isIncomplete);
  }

  private Boolean isEmpty(MedicalInvestigation medicalInvestigation) {
    if (medicalInvestigation == null) {
      return false;
    }
    return medicalInvestigation.date() != null || medicalInvestigation.investigationType() != null
        || medicalInvestigation.informationSource() != null;
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

  private boolean doesMedicalInvestigationListHaveValue(
      ElementValueMedicalInvestigationList value) {
    if (value.list() == null || value.list().isEmpty()) {
      return false;
    }

    return value.list().stream()
        .anyMatch(medicalInvestigation -> !isIncomplete(medicalInvestigation));
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
}
