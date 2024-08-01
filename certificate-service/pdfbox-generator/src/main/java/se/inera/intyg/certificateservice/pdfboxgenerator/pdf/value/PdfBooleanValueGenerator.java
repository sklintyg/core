package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.Collections;
import java.util.List;
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
    final var pdfConfiguration = (PdfConfigurationBoolean) elementSpecification.pdfConfiguration();
    return getFields(elementValue, pdfConfiguration);
  }

  private List<PdfField> getFields(ElementValueBoolean valueBoolean,
      PdfConfigurationBoolean configuration) {
    if (valueBoolean.value() == null) {
      return Collections.emptyList();
    }

    final var pdfFieldId = getCheckboxId(configuration, valueBoolean);

    return List.of(
        PdfField.builder()
            .id(pdfFieldId.id())
            .value(CHECKED_BOX_VALUE)
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
