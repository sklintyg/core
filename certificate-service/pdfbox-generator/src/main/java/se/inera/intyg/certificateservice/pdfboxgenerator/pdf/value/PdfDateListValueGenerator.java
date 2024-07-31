package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfQuestionField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.QuestionConfigurationDateList;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfDateListValueGenerator {

  public Class<? extends ElementValue> getType() {
    return ElementValueDateList.class;
  }

  public List<PdfField> generate(Certificate certificate, ElementId questionId, String fieldId) {
    if (certificate.elementData() == null || certificate.elementData().isEmpty()) {
      return Collections.emptyList();
    }

    final var question = certificate.getElementDataById(questionId);

    if (question.isEmpty()) {
      return Collections.emptyList();
    }

    if (!(question.get().value() instanceof ElementValueDateList elementValueDateList)) {
      throw new IllegalStateException(
          String.format(
              "Expected ElementValueDateList but was: '%s'",
              question.get().value().getClass()
          )
      );
    }

    return elementValueDateList.dateList().stream()
        .map(date -> getFields(date, getQuestionConfig(certificate)))
        .flatMap(Collection::stream)
        .toList();
  }

  private List<PdfField> getFields(ElementValueDate date,
      List<QuestionConfigurationDateList> configuration) {
    final var configForField = getConfigByFieldId(date, configuration);
    final var checkboxId = getCheckboxId(configForField);
    final var dateId = getDateId(configForField);

    if (date.date() != null) {
      return Stream.of(
              PdfField.builder()
                  .id(checkboxId != null ? checkboxId.id() : "")
                  .value(CHECKED_BOX_VALUE)
                  .build(),
              PdfField.builder()
                  .id(dateId != null ? dateId.id() : "")
                  .value(date.date().toString())
                  .build())
          .toList();
    }
    return Collections.emptyList();
  }

  private static List<QuestionConfigurationDateList> getQuestionConfig(
      Certificate certificate) {
    final var questionFields = certificate.certificateModel().pdfSpecification()
        .questionFields();

    return questionFields.stream()
        .map(PdfQuestionField::questionConfiguration)
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .filter(Objects::nonNull)
        .filter(QuestionConfigurationDateList.class::isInstance)
        .map(QuestionConfigurationDateList.class::cast)
        .toList();
  }

  private static List<QuestionConfigurationDateList> getConfigByFieldId(
      ElementValueDate date, List<QuestionConfigurationDateList> configuration) {

    return configuration.stream()
        .filter(config -> config.questionFieldId().equals(date.dateId()))
        .findFirst()
        .stream().toList();
  }

  private static PdfFieldId getCheckboxId(
      List<QuestionConfigurationDateList> configForQuestion) {

    return configForQuestion.stream()
        .map(QuestionConfigurationDateList::checkboxFieldId)
        .filter(Objects::nonNull)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "Could not get PdfFieldId from QuestionConfiguration in '%s'"
                .formatted(PdfDateListValueGenerator.class.getSimpleName()))
        );
  }

  private static PdfFieldId getDateId(
      List<QuestionConfigurationDateList> configForQuestion) {

    return configForQuestion.stream()
        .map(QuestionConfigurationDateList::dateFieldId)
        .filter(Objects::nonNull)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "Could not get PdfFieldId from QuestionConfiguration in '%s'"
                .formatted(PdfDateListValueGenerator.class.getSimpleName()))
        );

  }
}
