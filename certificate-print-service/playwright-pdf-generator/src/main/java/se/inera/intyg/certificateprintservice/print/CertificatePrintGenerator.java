package se.inera.intyg.certificateprintservice.print;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.PdfOptions;
import com.microsoft.playwright.Playwright;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import javax.swing.text.html.HTML.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateprintservice.print.api.Certificate;
import se.inera.intyg.certificateprintservice.print.api.Metadata;
import se.inera.intyg.certificateprintservice.print.converter.ElementCategoryConverter;

@Service
@Slf4j
@RequiredArgsConstructor
public class CertificatePrintGenerator implements PrintCertificateGenerator {


  private static final String CONTENT = "content";
  @Value("classpath:templates/certificateTemplate.html")
  private Resource template;
  @Value("classpath:templates/infoPageTemplate.html")
  private Resource infoPageTemplate;
  @Value("classpath:templates/tailwind_3.4.16.js")
  private Resource cssScript;
  private final Playwright playwright;

  private final ElementProvider elementProvider;

  @Override
  public byte[] generate(Certificate certificate) {

    try (
        Browser browser = playwright.chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(true));
        BrowserContext context = browser.newContext();
        Page detailsPage = context.newPage();
        Page infoPage = context.newPage();
        Page detailsHeaderPage = context.newPage();
        Page infoHeaderPage = context.newPage()
    ) {

      return PdfMerger.mergePdfs(createCertificateDetailsPage(certificate, detailsPage,
          detailsHeaderPage), createCertificateInfoPage(certificate.getMetadata(), infoPage,
          infoHeaderPage));

    } catch (Exception e) {
      throw new IllegalStateException("Failure creating pdf from certificate '%s'"
          .formatted(certificate.getMetadata().getCertificateId()), e);
    }
  }

  private byte[] createCertificateInfoPage(Metadata metadata, Page certificateInfoPage,
      Page infoHeaderPage) throws IOException, URISyntaxException {
    final var certificateInfoHeader = createHeader(metadata, false);
    final var certificateInfo = certificateInfoToHtml(metadata, infoHeaderPage,
        certificateInfoHeader);
    final var certificateInfoPdfOptions = getPdfOptions(metadata, certificateInfoHeader);
    certificateInfoPage.setContent(certificateInfo);
    return certificateInfoPage.pdf(certificateInfoPdfOptions);
  }

  private byte[] createCertificateDetailsPage(Certificate certificate, Page certificatePage,
      Page headerPage)
      throws IOException, URISyntaxException {
    final var certificateHeader = createHeader(certificate.getMetadata(), true);
    final var certificateContent = certificateToHtml(certificate, headerPage, certificateHeader);
    final var pdfOptions = getPdfOptions(certificate.getMetadata(), certificateHeader);
    certificatePage.setContent(certificateContent);
    return certificatePage.pdf(pdfOptions);

  }

  private String certificateToHtml(Certificate certificate, Page headerPage, String certificateHtml)
      throws IOException {
    var doc = createDocFromTemplate(template, certificateHtml, headerPage,
        certificate.getMetadata());

    final var content = doc.getElementById(CONTENT);
    certificate.getCategories().forEach(category ->
        Objects.requireNonNull(content).appendChild(ElementCategoryConverter.category(category)));

    log.debug(doc.html());
    return doc.html();
  }

  private String certificateInfoToHtml(Metadata metadata, Page infoHeaderPage,
      String certificateInfoHtml) throws IOException {

    var doc = createDocFromTemplate(infoPageTemplate, certificateInfoHtml, infoHeaderPage,
        metadata);

    final var content = doc.getElementById(CONTENT);

    assert content != null;
    content.appendChild(elementProvider.element(Tag.STRONG).text(metadata.getName()));
    content.appendChild(elementProvider.element(Tag.P).text(metadata.getDescription()));
    content.appendChild(elementProvider.element(Tag.STRONG).text("Skicka intyg till mottagare"));
    content.appendChild(elementProvider.element(Tag.P).text(TextFactory.citizenInformation()));
    return doc.html();
  }

  private Document createDocFromTemplate(Resource template, String html, Page page,
      Metadata metadata)
      throws IOException {
    final var headerHeight = getHeaderHeight(page, html);
    final var doc = Jsoup.parse(template.getFile(), StandardCharsets.UTF_8.name(), "",
        Parser.xmlParser());

    setPageMargin(doc, headerHeight);
    final var title = doc.getElementById("title");
    Objects.requireNonNull(title).appendText(getCertificateTitle(metadata));
    return doc;
  }

  private String getCertificateTitle(Metadata metadata) {
    return "%s (%s v%s)".formatted(metadata.getName(), metadata.getTypeId(),
        metadata.getVersion());
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

  private double getHeaderHeight(Page page, String header) {
    page.setContent(header);
    return page.getByTitle("headerElement").boundingBox().height;
  }

  private void setPageMargin(Document doc, Double headerHeight) {
    doc.getElementById("style").appendText("""
        @page {
              margin: calc(%spx + 6mm) 20mm 15mm 20mm;
            }""".formatted(Math.round(headerHeight)));
  }


  private String createFooterTemplate(Metadata certificateMetadata) {
    return """
            <div style="width: 100%%; text-align: left; font-size: 10px; margin-left: 30mm">
                <span>%s</span>
            </div>
        """.formatted(TextFactory.applicationOrigin(certificateMetadata));
  }

  private String createHeader(Metadata certificateMetadata, boolean includeConfidentialDetails)
      throws IOException, URISyntaxException {
    //TODO get from cert metadata
    final var logoPath = ClassLoader.getSystemResource("transportstyrelsen-logo.png")
        .toURI();

    final var baseWrapper = elementProvider.element(Tag.DIV);
    final var headerWrapper = elementProvider.headerWrapper();
    final var pageHeader = elementProvider.pageHeader(Files.readAllBytes(Paths.get(logoPath)));
    final var certificateHeader = elementProvider.certificateHeader(
        getCertificateTitle(certificateMetadata));

    if (includeConfidentialDetails) {
      pageHeader.appendChild(elementProvider.personId(certificateMetadata.getPersonId()));
      certificateHeader.appendChild(
          elementProvider.printInfo(TextFactory.information(certificateMetadata)));
      if (certificateMetadata.isSent()) {
        baseWrapper.appendChild(elementProvider.sent(certificateMetadata.getCertificateId()));
      }
    }

    headerWrapper.appendChild(pageHeader);
    headerWrapper.appendChild(certificateHeader);
    baseWrapper.appendChild(headerWrapper);
    baseWrapper.appendChild(
        elementProvider.leftMarginInfo(TextFactory.margin(certificateMetadata))
    );

    if (certificateMetadata.getSigningDate() != null) {
      baseWrapper.appendChild(elementProvider.draftWatermark());
    }

    return baseWrapper.html();
  }
}