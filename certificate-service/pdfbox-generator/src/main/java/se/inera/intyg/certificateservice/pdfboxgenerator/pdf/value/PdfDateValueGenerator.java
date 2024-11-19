package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDate;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfDateValueGenerator implements PdfElementValue<ElementValueDate> {

  @Override
  public Class<ElementValueDate> getType() {
    return ElementValueDate.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueDate elementValueDate) {
    if (elementValueDate.date() == null) {
      return Collections.emptyList();
    }

    final var pdfConfiguration = (PdfConfigurationDate) elementSpecification.pdfConfiguration();
    return List.of(
        PdfField.builder()
            .id(pdfConfiguration.pdfFieldId().id())
            .value(elementValueDate.date().toString())
            .build()
    );
  }
}
