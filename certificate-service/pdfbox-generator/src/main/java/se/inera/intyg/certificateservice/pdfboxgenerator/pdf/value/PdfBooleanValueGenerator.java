package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.QuestionConfigurationBoolean;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfBooleanValueGenerator implements PdfElementValue<ElementValueBoolean> {

  @Override
  public Class<ElementValueBoolean> getType() {
    return ElementValueBoolean.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueBoolean elementValue) {

    return getFields(elementValue,
        (List<QuestionConfigurationBoolean>) (List<?>) elementSpecification.printMapping()
            .questionConfiguration()
    );
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
