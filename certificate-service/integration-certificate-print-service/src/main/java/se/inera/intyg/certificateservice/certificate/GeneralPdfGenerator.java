package se.inera.intyg.certificateservice.certificate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.converter.PrintCertificateCategoryConverter;
import se.inera.intyg.certificateservice.certificate.converter.PrintCertificateMetadataConverter;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateRequestDTO;
import se.inera.intyg.certificateservice.certificate.integration.PrintCertificateFromCertificatePrintService;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;

@Component("GeneralPdfGenerator")
@AllArgsConstructor
public class GeneralPdfGenerator implements PdfGenerator {

  private final PrintCertificateMetadataConverter printCertificateMetadataConverter;
  private final PrintCertificateCategoryConverter printCertificateCategoryConverter;
  private final PrintCertificateFromCertificatePrintService printCertificateFromCertificatePrintService;

  @Override
  public Pdf generate(Certificate certificate, String additionalInfoText, boolean isCitizenFormat) {
    final var request = PrintCertificateRequestDTO.builder()
        .metadata(printCertificateMetadataConverter.convert(certificate, isCitizenFormat))
        .categories(
            certificate.certificateModel().elementSpecifications()
                .stream()
                .map(element -> printCertificateCategoryConverter.convert(certificate, element))
                .toList()
        )
        .build();

    final var response = printCertificateFromCertificatePrintService.print(request,
        certificate.id().id());

    return new Pdf(response.getPdfData(), getFileName(certificate));
  }

  private String getFileName(Certificate certificate) {
    return certificate.certificateModel().name()
        .replace("å", "a")
        .replace("ä", "a")
        .replace("ö", "o")
        .replace(" ", "_")
        .replace("–", "")
        .replace("__", "_")
        .toLowerCase();
  }

}
