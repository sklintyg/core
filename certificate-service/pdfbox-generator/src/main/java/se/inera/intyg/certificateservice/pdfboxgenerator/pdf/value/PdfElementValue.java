package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

public interface PdfElementValue<T extends ElementValue> {

  Class<T> getType();

  List<PdfField> generate(ElementSpecification elementSpecification, T elementValue);
}
