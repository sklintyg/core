package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationRadioBoolean;
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
    if (elementSpecification.pdfConfiguration() instanceof PdfConfigurationRadioBoolean radioConfig) {
      return getFields(elementValue, radioConfig);
    }

    final var pdfConfiguration = (PdfConfigurationBoolean) elementSpecification.pdfConfiguration();
    return getFields(elementValue, pdfConfiguration);
  }

  private List<PdfField> getFields(ElementValueBoolean valueBoolean,
      PdfConfigurationBoolean configuration) {
    if (valueBoolean.value() == null) {
      return Collections.emptyList();
    }

    if (configuration.checkboxFalse() == null && !valueBoolean.value()) {
      return List.of();
    }

    final var pdfFieldId = getCheckboxId(configuration, valueBoolean);

    return List.of(
        PdfField.builder()
            .id(pdfFieldId.id())
            .value(CHECKED_BOX_VALUE)
            .build()
    );
  }

  private List<PdfField> getFields(ElementValueBoolean valueBoolean,
      PdfConfigurationRadioBoolean configuration) {
    if (valueBoolean.value() == null) {
      return Collections.emptyList();
    }

    return List.of(
        PdfField.builder()
            .id(configuration.pdfFieldId().id())
            .value(valueBoolean.value() ?
                configuration.optionTrue().value()
                : configuration.optionFalse().value()
            )
            .build()
    );
  }

  private static PdfFieldId getCheckboxId(PdfConfigurationBoolean configForQuestion,
      ElementValueBoolean valueBoolean) {
    if (Boolean.FALSE.equals(valueBoolean.value())) {
      return configForQuestion.checkboxFalse();
    }
    return configForQuestion.checkboxTrue();
  }
}
