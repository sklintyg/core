package se.inera.intyg.certificateprintservice.playwright;

import com.microsoft.playwright.Browser;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateprintservice.pdfgenerator.PrintCertificateGenerator;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.playwright.certificate.CertificateToHtmlConverter;
import se.inera.intyg.certificateprintservice.playwright.certificate.PrintInformation;
import se.inera.intyg.certificateprintservice.playwright.element.FooterConverter;
import se.inera.intyg.certificateprintservice.playwright.element.HeaderConverter;

@Service
@Slf4j
@RequiredArgsConstructor
public class CertificatePrintGenerator implements PrintCertificateGenerator {

  @Value("classpath:templates/certificateTemplate.html")
  private Resource template;
  private final CertificateToHtmlConverter certificateToHtmlConverter;
  private final Browser browser;


  @Override
  public byte[] generate(final Certificate certificate) {
    try {
      final var printInformation = new PrintInformation();
      printInformation.setHeaderHtml(HeaderConverter.createHeader(certificate.getMetadata()));
      printInformation.setFooterHtml(FooterConverter.createFooter(certificate.getMetadata()));
      printInformation.setCertificate(certificate);
      printInformation.setTemplate(template.getInputStream());

      return createCertificatePdf(printInformation);
    } catch (Exception e) {
      throw new IllegalStateException("Failure creating pdf from certificate '%s'"
          .formatted(certificate.getMetadata().getCertificateId()), e);
    }
  }

  private byte[] createCertificatePdf(PrintInformation printInformation) {
    final var start1 = Instant.now();

    try (
        final var context = browser.newContext();
        final var page = context.newPage();
        final var headerPage = context.newPage()
    ) {
      log.info("Details resources time: {}", Duration.between(start1, Instant.now()).toMillis());
      printInformation.setHeaderPage(headerPage);
      final var certificateContent = certificateToHtmlConverter.certificate(printInformation);
      final var start2 = Instant.now();
      page.setContent(certificateContent);
      log.info("Details pdf time: {}", Duration.between(start2, Instant.now()).toMillis());
      return page.pdf(printInformation.getPdfOptions());
    } catch (Exception e) {
      throw new IllegalStateException("Failure creating certificate details pdf", e);
    }
  }
}