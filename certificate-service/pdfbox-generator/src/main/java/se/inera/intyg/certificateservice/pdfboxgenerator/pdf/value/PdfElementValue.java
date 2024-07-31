package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

public interface PdfElementValue {

  Class<? extends ElementValue> getType();

  List<PdfField> generate(Certificate certificate, ElementId questionId, String fieldId);
}
