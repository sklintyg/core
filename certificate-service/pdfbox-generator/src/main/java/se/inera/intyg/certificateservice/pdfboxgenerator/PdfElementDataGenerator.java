package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

public interface PdfElementDataGenerator {

  void generate(PDAcroForm acroForm, Certificate certificate, ElementId id, String fieldName)
      throws IOException;

}
