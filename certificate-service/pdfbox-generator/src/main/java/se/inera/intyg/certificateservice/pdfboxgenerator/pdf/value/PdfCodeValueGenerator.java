package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.QuestionConfigurationCode;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfCodeValueGenerator implements PdfElementValue<ElementValueCode> {

  @Override
  public Class<ElementValueCode> getType() {
    return ElementValueCode.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueCode elementValueCode) {
    return getField(elementValueCode,
        (List<QuestionConfigurationCode>) (List<?>) elementSpecification.printMapping()
            .questionConfiguration()
    );
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
