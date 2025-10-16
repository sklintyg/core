package se.inera.intyg.certificateservice.certificate;

import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.converter.PrintCertificateCategoryConverter;
import se.inera.intyg.certificateservice.certificate.converter.PrintCertificateMetadataConverter;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateCategoryDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateRequestDTO;
import se.inera.intyg.certificateservice.certificate.integration.PrintCertificateFromCertificatePrintService;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@Component("generalPdfGenerator")
@AllArgsConstructor
public class GeneralPdfGenerator implements PdfGenerator {

  private final PrintCertificateMetadataConverter printCertificateMetadataConverter;
  private final PrintCertificateCategoryConverter printCertificateCategoryConverter;
  private final PrintCertificateFromCertificatePrintService printCertificateFromCertificatePrintService;

  @Override
  public Pdf generate(Certificate certificate, PdfGeneratorOptions options) {
    final var fileName = getFileName(certificate);

    final var request = PrintCertificateRequestDTO.builder()
        .metadata(printCertificateMetadataConverter.convert(certificate, options.citizenFormat(),
            fileName))
        .categories(
            certificate.certificateModel().elementSpecifications()
                .stream()
                .filter(isNotContactInformation())
                .map(element -> printCertificateCategoryConverter.convert(
                    certificate,
                    element,
                    options)
                )
                .filter(hasQuestions())
                .toList()
        )
        .build();

    final var response = printCertificateFromCertificatePrintService.print(request);

    return new Pdf(response.getPdfData(), fileName);
  }

  private String getFileName(Certificate certificate) {
    final var name = certificate.certificateModel().recipient().generalName() == null
        ? certificate.certificateModel().name()
        : certificate.certificateModel().recipient().generalName();

    return name
        .replace("å", "a")
        .replace("ä", "a")
        .replace("ö", "o")
        .replace(" ", "_")
        .replace("–", "")
        .replace("__", "_")
        .toLowerCase();
  }


  private Predicate<ElementSpecification> isNotContactInformation() {
    return elementSpecification -> !(elementSpecification.configuration() instanceof ElementConfigurationUnitContactInformation);
  }

  private Predicate<PrintCertificateCategoryDTO> hasQuestions() {
    return elementSpecification -> !elementSpecification.getQuestions().isEmpty();
  }

}

