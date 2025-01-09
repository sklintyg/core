package se.inera.intyg.certificateprintservice.playwright;

import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateprintservice.pdfgenerator.PrintCertificateGenerator;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.playwright.browserpool.BrowserPool;
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
  private final BrowserPool browserPool;
  private final CertificateToHtmlConverter certificateToHtmlConverter;

  @Override
  public byte[] generate(final Certificate certificate) {
    try {
      final var start1 = Instant.now();
      final var printInformation = new PrintInformation();
      printInformation.setHeaderHtml(HeaderConverter.createHeader(certificate.getMetadata()));
      printInformation.setFooterHtml(FooterConverter.createFooter(certificate.getMetadata()));
      printInformation.setCertificate(certificate);
      printInformation.setTemplate(template.getInputStream());
      log.info("Build html time: {}", Duration.between(start1, Instant.now()).toMillis());

      return createCertificatePdf(printInformation);
    } catch (Exception e) {
      throw new IllegalStateException("Failure creating pdf from certificate '%s'"
          .formatted(certificate.getMetadata().getCertificateId()), e);
    }
  }

  private byte[] createCertificatePdf(PrintInformation printInformation) throws Exception {
    final var start1 = Instant.now();
    final var playwrightBrowser = browserPool.borrowObject();
    final var browser = playwrightBrowser.getBrowser();
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
      final var pdf = page.pdf(printInformation.getPdfOptions());
      log.info("Details pdf time: {}", Duration.between(start2, Instant.now()).toMillis());
      return pdf;

    } catch (Exception e) {
      throw new IllegalStateException("Failure creating certificate details pdf", e);

    } finally {
      browserPool.returnObject(playwrightBrowser);
    }
  }

}