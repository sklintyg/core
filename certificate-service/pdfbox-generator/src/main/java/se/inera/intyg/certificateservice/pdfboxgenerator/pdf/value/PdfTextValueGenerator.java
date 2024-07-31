package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfTextValueGenerator implements PdfElementValue<ElementValueText> {

  @Override
  public Class<ElementValueText> getType() {
    return ElementValueText.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueText elementValueText) {
    return List.of(
        PdfField.builder()
            .id(elementSpecification.printMapping().pdfFieldId().id())
            .value(elementValueText.text())
            .build()
    );
  }
}
