package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;

public interface PdfCertificateValueGenerator {

  void fillDocument(PDAcroForm acroForm, Certificate certificate) throws IOException;

  CertificateType getType();

}
