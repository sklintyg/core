package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
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

    if (!(elementSpecification.printMapping()
        .pdfConfiguration() instanceof PdfConfigurationBoolean pdfConfigurationBoolean)) {
      throw new IllegalArgumentException(
          "Not a Boolean mapping: " + elementSpecification.printMapping());
    }

    return getFields(elementValue, pdfConfigurationBoolean);
  }

  private List<PdfField> getFields(ElementValueBoolean valueBoolean,
      PdfConfigurationBoolean configuration) {
    final var pdfFieldId = getCheckboxId(configuration, valueBoolean);

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

  private static PdfFieldId getCheckboxId(PdfConfigurationBoolean configForQuestion,
      ElementValueBoolean valueBoolean) {
    if (Boolean.FALSE.equals(valueBoolean.value())) {
      return configForQuestion.checkboxFalse();
    }
    if (Boolean.TRUE.equals(valueBoolean.value())) {
      return configForQuestion.checkboxTrue();
    }
    return null;
  }
}
