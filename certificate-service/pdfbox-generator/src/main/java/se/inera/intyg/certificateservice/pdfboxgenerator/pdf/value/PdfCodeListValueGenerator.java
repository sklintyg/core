package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationCode;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfCodeListValueGenerator implements PdfElementValue<ElementValueCodeList> {

  @Override
  public Class<ElementValueCodeList> getType() {
    return ElementValueCodeList.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueCodeList elementValueCodeList) {
    final var pdfConfiguration = (PdfConfigurationCode) elementSpecification.pdfConfiguration();
    return elementValueCodeList.list().stream()
        .map(code -> getField(code, pdfConfiguration))
        .flatMap(Collection::stream)
        .toList();
  }

  private List<PdfField> getField(ElementValueCode code, PdfConfigurationCode configuration) {
    if (codeIsInvalid(code)) {
      return Collections.emptyList();
    }

    final var codeId = configuration.codes().get(code.codeId());
    if (codeId == null) {
      throw new IllegalArgumentException("No checkbox found for date: " + code.codeId());
    }

    return List.of(
        PdfField.builder()
            .id(codeId.id())
            .value(CHECKED_BOX_VALUE)
            .build()
    );
  }

  private static boolean codeIsInvalid(ElementValueCode code) {
    return code == null || code.codeId() == null || code.codeId().value() == null;
  }
}
