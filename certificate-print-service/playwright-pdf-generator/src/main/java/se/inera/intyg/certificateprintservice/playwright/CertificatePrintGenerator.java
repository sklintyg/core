package se.inera.intyg.certificateprintservice.playwright;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page.PdfOptions;
import com.microsoft.playwright.Playwright;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import javax.swing.text.html.HTML.Tag;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateprintservice.pdfgenerator.PrintCertificateGenerator;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.certificate.CertificateToHtmlConverter;
import se.inera.intyg.certificateprintservice.playwright.element.ElementProvider;
import se.inera.intyg.certificateprintservice.playwright.element.HeaderConverter;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

@Service
@Slf4j
public class CertificatePrintGenerator implements PrintCertificateGenerator, InitializingBean {

  @Value("classpath:templates/certificateTemplate.html")
  private Resource template;
  @Value("classpath:templates/infoPageTemplate.html")
  private Resource infoPageTemplate;

  private final Playwright playwrightCertificateDetails;
  private final Playwright playwrightCertificateInfo;
  private final CertificateToHtmlConverter certificateToHtmlConverter;

  private static final LaunchOptions LAUNCH_OPTIONS = new LaunchOptions().setHeadless(true);
  private Browser browserCertificateDetails;
  private Browser browserCertificateInfo;

  public CertificatePrintGenerator(
      @Qualifier("playwrightCertificateDetails") Playwright playwrightCertificateDetails,
      @Qualifier("playwrightCertificateInfo") Playwright playwrightCertificateInfo,
      CertificateToHtmlConverter certificateToHtmlConverter) {
    this.playwrightCertificateDetails = playwrightCertificateDetails;
    this.playwrightCertificateInfo = playwrightCertificateInfo;
    this.certificateToHtmlConverter = certificateToHtmlConverter;
  }

  @Override
  public void afterPropertiesSet() {
    browserCertificateDetails = playwrightCertificateDetails.chromium()
        .launch(LAUNCH_OPTIONS);
    browserCertificateInfo = playwrightCertificateInfo.chromium()
        .launch(LAUNCH_OPTIONS);
  }

  @Override
  public byte[] generate(final Certificate certificate) {
    try {
      final var start = Instant.now();

      final var certificateDetailsPdf = CompletableFuture.supplyAsync(() ->
          createCertificateDetailsPage(certificate));
      final var certificateInfoPdf = CompletableFuture.supplyAsync(() ->
          createCertificateInfoPage(certificate.getMetadata()));

      final var t = certificateDetailsPdf.thenCombine(certificateInfoPdf, PdfMerger::mergePdfs)
          .get();
      log.info("Total time: {}", Duration.between(start, Instant.now()).toMillis());
      return t;

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException(e);

    } catch (Exception e) {
      throw new IllegalStateException("Failure creating pdf from certificate '%s'"
          .formatted(certificate.getMetadata().getCertificateId()), e);
    }
  }


  private byte[] createCertificateInfoPage(Metadata metadata) {
    final var start1 = Instant.now();
    try (
        final var context = browserCertificateInfo.newContext();
        final var infoPage = context.newPage();
        final var infoHeaderPage = context.newPage()
    ) {
      log.info("info resources time: {}", Duration.between(start1, Instant.now()).toMillis());
      final var certificateInfoHeader = HeaderConverter.header(metadata, false);
      final var certificateInfo = certificateToHtmlConverter.certificateInformation(
          infoPageTemplate,
          infoHeaderPage, certificateInfoHeader, metadata);
      final var certificateInfoPdfOptions = getPdfOptions(metadata, certificateInfoHeader);
      final var start2 = Instant.now();
      infoPage.setContent(certificateInfo);
      final var t = infoPage.pdf(certificateInfoPdfOptions);
      log.info("Info pdf time: {}", Duration.between(start2, Instant.now()).toMillis());
      return t;
    } catch (Exception e) {
      throw new IllegalStateException("Failure creating certificate info pdf", e);
    }
  }

  private byte[] createCertificateDetailsPage(Certificate certificate) {
    final var start1 = Instant.now();
    try (
        final var context = browserCertificateDetails.newContext();
        final var detailsPage = context.newPage();
        final var detailsHeaderPage = context.newPage()
    ) {
      log.info("Details resources time: {}", Duration.between(start1, Instant.now()).toMillis());

      final var certificateHeader = HeaderConverter.header(certificate.getMetadata(), true);
      final var certificateContent = certificateToHtmlConverter.certificate(template, certificate,
          detailsHeaderPage, certificateHeader);
      final var pdfOptions = getPdfOptions(certificate.getMetadata(), certificateHeader);
      final var start2 = Instant.now();
      detailsPage.setContent(certificateContent);
      final var t = detailsPage.pdf(pdfOptions);
      log.info("Details pdf time: {}", Duration.between(start2, Instant.now()).toMillis());
      return t;
    } catch (Exception e) {
      throw new IllegalStateException("Failure creating certificate details pdf", e);
    }
  }


  private PdfOptions getPdfOptions(Metadata metadata, String header) {
    return new PdfOptions()
        .setFormat("A4")
        .setPrintBackground(true)
        .setDisplayHeaderFooter(true)
        .setTagged(true)
        .setHeaderTemplate(header)
        .setFooterTemplate(createFooterTemplate(metadata));
  }

  private String createFooterTemplate(Metadata metadata) {
    final var elementList = new ArrayList<Element>();
    final var baseWrapper = ElementProvider.element(Tag.DIV);
    final var footerWrapper = ElementProvider.element(Tag.DIV)
        .attr(STYLE, """
            height: 25mm;
            width: 100%;
            font-size: 10pt;
            margin: 0 20mm;
            border-top: black solid 1px;
            """);
    elementList.add(ElementProvider.element(Tag.SPAN)
        .attr(STYLE, """
            display: block;
            margin-top: 5mm;
            margin-bottom: 2mm;
            """)
        .text(TextFactory.applicationOrigin(metadata)));

    elementList.add(ElementProvider.element(Tag.A)
        .attr("href", "https://inera.se")
        .text("www.inera.se"));

    footerWrapper.appendChildren(elementList);
    baseWrapper.appendChild(footerWrapper);
    return baseWrapper.html();
  }
}