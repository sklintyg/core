package se.inera.intyg.certificateprintservice.playwright;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;

import com.microsoft.playwright.Page.PdfOptions;
import java.nio.charset.StandardCharsets;
import javax.swing.text.html.HTML.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateprintservice.pdfgenerator.PrintCertificateGenerator;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.playwright.browserpool.BrowserPool;
import se.inera.intyg.certificateprintservice.playwright.browserpool.PlaywrightBrowser;
import se.inera.intyg.certificateprintservice.playwright.certificate.HtmlConverter;
import se.inera.intyg.certificateprintservice.playwright.element.ContentConverter;
import se.inera.intyg.certificateprintservice.playwright.element.FooterConverter;
import se.inera.intyg.certificateprintservice.playwright.element.HeaderConverter;
import se.inera.intyg.certificateprintservice.playwright.element.LeftMarginInfoConverter;
import se.inera.intyg.certificateprintservice.playwright.pdf.TemplateToDocumentConverter;

@Service
@Slf4j
@RequiredArgsConstructor
public class CertificatePrintGenerator implements PrintCertificateGenerator {

  @Value("classpath:templates/certificateTemplates.html")
  private Resource template;
  private final BrowserPool browserPool;
  private final HtmlConverter htmlConverter;
  private final TemplateToDocumentConverter templateToDocumentConverter;

  @Override
  public byte[] generate(final Certificate certificate) {
    try {

      return createCertificatePdf(certificate);

    } catch (Exception e) {
      throw new IllegalStateException("Failure creating pdf from certificate '%s'"
          .formatted(certificate.getMetadata().getCertificateId()), e);
    }
  }

  private byte[] createCertificatePdf(Certificate certificate) {
    PlaywrightBrowser playwrightBrowser = null;
    try {
      playwrightBrowser = browserPool.borrowObject();
      return createPdf(playwrightBrowser, certificate);
    } catch (Exception e) {
      throw new IllegalStateException("Failure creating certificate details pdf", e);
    } finally {
      browserPool.returnObject(playwrightBrowser);
    }

  }

  private byte[] createPdf(PlaywrightBrowser playwrightBrowser, Certificate certificate) {
    try (
        final var context = playwrightBrowser.getBrowserContext();
        final var detailsPage = context.newPage();
//        final var headerPage = context.newPage()
    ) {
      final var metadata = certificate.getMetadata();
      final var header = HeaderConverter.convert(metadata).create();
      final var footer = FooterConverter.convert(metadata).create();
      final var content = ContentConverter.convert(certificate).create();
      final var leftMarginInfo = LeftMarginInfoConverter.convert(metadata).create();

      final var document = Jsoup.parse(template.getInputStream(), StandardCharsets.UTF_8.name(),
          "", Parser.xmlParser());

      document.getElementsByTag(Tag.SCRIPT.toString())
          .attr("src", templateToDocumentConverter.getScriptSource());
      document.getElementById("header").appendChild(header);

      final var headerHeight3 = templateToDocumentConverter.calculateHeaderHeight(detailsPage,
          document.html(), "header");

      document.getElementById("footer").appendChild(footer);
      document.getElementById("content").appendChild(content);
      document.getElementById("leftMarginInfo").appendChild(leftMarginInfo);

      document.getElementById("header-space").appendChild(header)
          .attr(STYLE, "height: calc(%spx);".formatted(headerHeight3));

      templateToDocumentConverter.setDocumentTitle(document, certificate.getMetadata());

      detailsPage.setContent(document.html());

      return detailsPage.pdf(getPdfOptions());

    } catch (Exception e) {
      throw new IllegalStateException("Failure creating certificate details pdf", e);
    }
  }

  public PdfOptions getPdfOptions() {
    return new PdfOptions()
        .setFormat("A4")
        .setPrintBackground(true)
        .setDisplayHeaderFooter(false);
  }


}