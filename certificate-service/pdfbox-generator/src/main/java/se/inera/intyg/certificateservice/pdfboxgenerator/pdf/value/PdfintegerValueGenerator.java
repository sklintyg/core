package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfintegerValueGenerator implements PdfElementValue<ElementValueInteger> {

  @Override
  public Class<ElementValueInteger> getType() {
    return ElementValueInteger.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueInteger elementValueInteger) {
    if (elementValueInteger.value() == null) {
      return Collections.emptyList();
    }

    final var pdfConfiguration = (PdfConfigurationText) elementSpecification.pdfConfiguration();

    return List.of(
        PdfField.builder()
            .id(pdfConfiguration.pdfFieldId().id())
            .value(elementValueInteger.value().toString())
            .offset(pdfConfiguration.offset())
            .build()
    );
  }
}
