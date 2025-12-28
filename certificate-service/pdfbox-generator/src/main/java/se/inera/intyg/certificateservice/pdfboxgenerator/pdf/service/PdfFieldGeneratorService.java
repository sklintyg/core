package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificatePdfContext;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfElementValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfPatientValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfSignatureValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfUnitValueGenerator;

@RequiredArgsConstructor
@Service
public class PdfFieldGeneratorService {

  private final PdfUnitValueGenerator pdfUnitValueGenerator;
  private final PdfPatientValueGenerator pdfPatientValueGenerator;
  private final PdfSignatureValueGenerator pdfSignatureValueGenerator;
  private final PdfElementValueGenerator pdfElementValueGenerator;

  public List<PdfField> generatePdfFields(CertificatePdfContext context) {
    final var certificate = context.getCertificate();

    return Stream.of(
        pdfUnitValueGenerator.generate(certificate),
        pdfPatientValueGenerator.generate(context.getCertificate(),
            context.getTemplatePdfSpecification().patientIdFieldIds()),
        getSignatureFields(certificate),
        pdfElementValueGenerator.generate(certificate)
    ).flatMap(Collection::stream).toList();
  }

  private List<PdfField> getSignatureFields(Certificate certificate) {
    return certificate.status() == Status.SIGNED
        ? pdfSignatureValueGenerator.generate(certificate)
        : List.of();
  }
}
