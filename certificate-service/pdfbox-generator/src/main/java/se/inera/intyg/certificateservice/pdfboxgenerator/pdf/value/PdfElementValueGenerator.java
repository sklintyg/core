package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.io.IOException;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

public interface PdfElementValueGenerator {

  List<PdfField> generate(Certificate certificate, ElementId id, String fieldId)
      throws IOException;

}
