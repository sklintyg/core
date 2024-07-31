package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfQuestionField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.QuestionConfigurationDiagnose;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfDiagnosisListValueGenerator {

  public Class<? extends ElementValue> getType() {
    return ElementValueDiagnosis.class;
  }

  public List<PdfField> generate(Certificate certificate, ElementId questionId, String fieldId) {
    if (certificate.elementData() == null || certificate.elementData().isEmpty()) {
      return Collections.emptyList();
    }

    final var question = certificate.getElementDataById(questionId);

    if (question.isEmpty()) {
      return Collections.emptyList();
    }

    if (!(question.get().value() instanceof ElementValueDiagnosisList elementValueDiagnosisList)) {
      throw new IllegalStateException(
          String.format(
              "Expected ElementValueDiagnosisList but was: '%s'",
              question.get().value().getClass()
          )
      );
    }

    return elementValueDiagnosisList.diagnoses().stream()
        .map(diagnose -> getFields(diagnose, getQuestionConfig(certificate)))
        .flatMap(Collection::stream)
        .toList();
  }

  private List<PdfField> getFields(ElementValueDiagnosis diagnose,
      List<QuestionConfigurationDiagnose> configuration) {

    final var configForField = getConfigByFieldId(diagnose, configuration);
    final var diagnoseNameId = getDiagnoseNameId(configForField);
    final var diagnoseCodeIds = getDiagnoseCodeIds(configForField);

    return Stream.of(
            getDiagnoseNameValues(diagnose, diagnoseNameId != null ? diagnoseNameId.id() : ""),
            getDiagnoseCodeValues(diagnose, diagnoseCodeIds))
        .flatMap(Collection::stream)
        .toList();
  }

  private static List<QuestionConfigurationDiagnose> getQuestionConfig(
      Certificate certificate) {
    final var questionFields = certificate.certificateModel().pdfSpecification()
        .questionFields();

    return questionFields.stream()
        .map(PdfQuestionField::questionConfiguration)
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .filter(Objects::nonNull)
        .filter(QuestionConfigurationDiagnose.class::isInstance)
        .map(QuestionConfigurationDiagnose.class::cast)
        .toList();
  }

  private static List<QuestionConfigurationDiagnose> getConfigByFieldId(
      ElementValueDiagnosis diagnose, List<QuestionConfigurationDiagnose> configuration) {

    return configuration.stream()
        .filter(config -> config.questionId().equals(diagnose.id()))
        .findFirst()
        .stream().toList();
  }

  private static PdfFieldId getDiagnoseNameId(
      List<QuestionConfigurationDiagnose> questionConfig) {

    return questionConfig.stream()
        .map(QuestionConfigurationDiagnose::diagnoseNameFieldId)
        .filter(Objects::nonNull)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "Could not get PdfFieldId from QuestionConfiguration in '%s'"
                .formatted(PdfDiagnosisListValueGenerator.class))
        );
  }

  private static List<PdfFieldId> getDiagnoseCodeIds(
      List<QuestionConfigurationDiagnose> questionConfig) {
    return questionConfig.stream()
        .map(QuestionConfigurationDiagnose::diagnoseCodeFieldIds)
        .flatMap(Collection::stream)
        .toList();
  }

  private List<PdfField> getDiagnoseNameValues(ElementValueDiagnosis diagnose,
      String pdfFieldId) {
    return List.of(PdfField.builder()
        .id(pdfFieldId)
        .value(diagnose.description())
        .build());
  }

  private static List<PdfField> getDiagnoseCodeValues(ElementValueDiagnosis diagnose,
      List<PdfFieldId> codeIds) {
    final var fields = new ArrayList<PdfField>();
    final var codes = diagnose.code().toCharArray();

    for (var i = 0; i < codes.length; i++) {
      fields.add(getDiagnoseCode(String.valueOf(codes[i]), codeIds.get(i).id()));
    }

    return fields;
  }

  private static PdfField getDiagnoseCode(String value, String pdfFieldId) {
    return PdfField.builder()
        .id(pdfFieldId)
        .value(value)
        .build();
  }

}
