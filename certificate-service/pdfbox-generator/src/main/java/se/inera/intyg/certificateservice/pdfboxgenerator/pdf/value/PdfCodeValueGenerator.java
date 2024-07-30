package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfQuestionField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfValueType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.QuestionConfigurationCode;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfCodeValueGenerator implements PdfElementValue {

  @Override
  public PdfValueType getType() {
    return PdfValueType.CODE;
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

    if (!(question.get().value() instanceof ElementValueCode elementValueCode)) {
      throw new IllegalStateException(
          String.format(
              "Expected ElementValueCode but was: '%s'",
              question.get().value().getClass()
          )
      );
    }

    return getField(elementValueCode, getQuestionConfig(certificate));

  }

  private List<PdfField> getField(ElementValueCode code,
      List<QuestionConfigurationCode> configuration) {
    final var configForField = getConfigByFieldId(code, configuration);
    final var codeId = getCodeId(configForField);

    if (code != null) {
      return Stream.of(
              PdfField.builder()
                  .id(codeId != null ? codeId.id() : "")
                  .value(CHECKED_BOX_VALUE)
                  .build())
          .toList();
    }
    return Collections.emptyList();
  }

  private static List<QuestionConfigurationCode> getQuestionConfig(
      Certificate certificate) {
    final var questionFields = certificate.certificateModel().pdfSpecification()
        .questionFields();

    return questionFields.stream()
        .map(PdfQuestionField::questionConfiguration)
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .filter(Objects::nonNull)
        .filter(QuestionConfigurationCode.class::isInstance)
        .map(QuestionConfigurationCode.class::cast)
        .toList();
  }

  private static List<QuestionConfigurationCode> getConfigByFieldId(
      ElementValueCode code, List<QuestionConfigurationCode> configuration) {

    return configuration.stream()
        .filter(config -> config.questionFieldId().equals(code.codeId()))
        .findFirst()
        .stream().toList();
  }

  private static PdfFieldId getCodeId(
      List<QuestionConfigurationCode> configForQuestion) {

    return configForQuestion.stream()
        .map(QuestionConfigurationCode::pdfFieldId)
        .filter(Objects::nonNull)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "Could not get PdfFieldId from QuestionConfiguration in '%s'"
                .formatted(PdfCodeValueGenerator.class.getSimpleName()))
        );
  }
}
