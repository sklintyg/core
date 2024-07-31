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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfQuestionField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.QuestionConfigurationBoolean;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfBooleanValueGenerator implements PdfElementValue {

  @Override
  public Class<? extends ElementValue> getType() {
    return ElementValueBoolean.class;
  }

  @Override
  public List<PdfField> generate(Certificate certificate, ElementId questionId, String fieldId) {
    if (certificate.elementData() == null || certificate.elementData().isEmpty()) {
      return Collections.emptyList();
    }

    final var question = certificate.getElementDataById(questionId);

    if (question.isEmpty()) {
      return Collections.emptyList();
    }

    if (!(question.get().value() instanceof ElementValueBoolean elementValueBoolean)) {
      throw new IllegalStateException(
          String.format(
              "Expected ElementValueBoolean but was: '%s'",
              question.get().value().getClass()
          )
      );
    }

    return getFields(elementValueBoolean, getQuestionConfig(certificate));
  }

  private List<PdfField> getFields(ElementValueBoolean valueBoolean,
      List<QuestionConfigurationBoolean> configuration) {
    final var configForField = getConfigByFieldId(valueBoolean, configuration);
    final var pdfFieldId = getCheckboxId(configForField, valueBoolean);

    if (valueBoolean.value() != null) {
      return Stream.of(
              PdfField.builder()
                  .id(pdfFieldId != null ? pdfFieldId.id() : "")
                  .value(CHECKED_BOX_VALUE)
                  .build())
          .toList();
    }
    return Collections.emptyList();
  }

  private static List<QuestionConfigurationBoolean> getQuestionConfig(Certificate certificate) {
    final var questionFields = certificate.certificateModel().pdfSpecification()
        .questionFields();

    return questionFields.stream()
        .map(PdfQuestionField::questionConfiguration)
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .filter(Objects::nonNull)
        .filter(QuestionConfigurationBoolean.class::isInstance)
        .map(QuestionConfigurationBoolean.class::cast)
        .toList();
  }

  private static List<QuestionConfigurationBoolean> getConfigByFieldId(
      ElementValueBoolean value, List<QuestionConfigurationBoolean> configuration) {
    return configuration.stream()
        .filter(config -> config.questionId().equals(value.booleanId()))
        .findFirst()
        .stream()
        .toList();
  }

  private static PdfFieldId getCheckboxId(List<QuestionConfigurationBoolean> configForQuestion,
      ElementValueBoolean valueBoolean) {

    if (Boolean.FALSE.equals(valueBoolean.value())) {
      return configForQuestion.stream()
          .map(QuestionConfigurationBoolean::checkboxFalse)
          .findFirst()
          .orElseThrow(() -> new IllegalStateException(
              "Could not find PdfFieldId for ElementValue with FieldId '%s'"
                  .formatted(valueBoolean.booleanId().value()))
          );
    }
    if (Boolean.TRUE.equals(valueBoolean.value())) {
      return configForQuestion.stream()
          .map(QuestionConfigurationBoolean::checkboxTrue)
          .findFirst()
          .orElseThrow(() -> new IllegalStateException(
              "Could not find PdfFieldId for ElementValue with FieldId '%s'"
                  .formatted(valueBoolean.booleanId().value()))
          );
    }
    return null;
  }
}
