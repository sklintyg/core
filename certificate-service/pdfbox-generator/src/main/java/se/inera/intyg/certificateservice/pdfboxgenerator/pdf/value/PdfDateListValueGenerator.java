package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.QuestionConfigurationDateList;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfDateListValueGenerator implements PdfElementValue<ElementValueDateList> {

  @Override
  public Class<ElementValueDateList> getType() {
    return ElementValueDateList.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueDateList elementValueDateList) {
    final var questionConfigurations = (List<QuestionConfigurationDateList>) (List<?>)
        elementSpecification.printMapping().questionConfiguration();
    return elementValueDateList.dateList().stream()
        .map(date -> getFields(date, questionConfigurations))
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
