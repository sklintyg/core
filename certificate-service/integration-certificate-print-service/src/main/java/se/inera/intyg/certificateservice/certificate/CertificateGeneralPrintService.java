package se.inera.intyg.certificateservice.certificate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.converter.PrintCertificateQuestionConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;

@Component("generalPdfService")
@AllArgsConstructor
public class CertificateGeneralPrintService implements PdfGenerator {

  private final PrintCertificateQuestionConverter printCertificateQuestionConverter;

  @Override
  public Pdf generate(Certificate certificate, String additionalInfoText, boolean isCitizenFormat) {
    // Convert request
    // Make call to PrintCertificateFromCerfiticatePrintService
    // Convert response

    return null;
  }
}
