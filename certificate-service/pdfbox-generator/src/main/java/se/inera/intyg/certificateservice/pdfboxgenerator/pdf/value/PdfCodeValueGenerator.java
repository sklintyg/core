package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationCode;
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
    final var pdfConfiguration = (PdfConfigurationCode) elementSpecification.pdfConfiguration();
    return getField(elementValueCode, pdfConfiguration);
  }

  private List<PdfField> getField(ElementValueCode code, PdfConfigurationCode configuration) {
    if (code == null) {
      return Collections.emptyList();
    }

    final var codeId = configuration.codes().get(code.codeId());
    if (codeId == null) {
      throw new IllegalArgumentException("Code " + code.codeId() + " not found");
    }

    return List.of(
        PdfField.builder()
            .id(codeId.id())
            .value(CHECKED_BOX_VALUE)
            .build()
    );
  }
}
