package se.inera.intyg.certificateprintservice.print;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.PdfOptions;
import com.microsoft.playwright.Playwright;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
import se.inera.intyg.certificateprintservice.print.converter.CategoryConverter;
import se.inera.intyg.certificateprintservice.print.converter.HeaderConverter;

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

  @Override
  public byte[] generate(Certificate certificate) {

    try (
        final var browser = playwright.chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(true));
        final var context = browser.newContext();
        final var detailsPage = context.newPage();
        final var infoPage = context.newPage();
        final var detailsHeaderPage = context.newPage();
        final var infoHeaderPage = context.newPage()
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
      Page infoHeaderPage) throws IOException {
    final var certificateInfoHeader = HeaderConverter.header(metadata, false);
    final var certificateInfo = certificateInfoToHtml(metadata, infoHeaderPage,
        certificateInfoHeader);
    final var certificateInfoPdfOptions = getPdfOptions(metadata, certificateInfoHeader);
    certificateInfoPage.setContent(certificateInfo);
    return certificateInfoPage.pdf(certificateInfoPdfOptions);
  }

  private byte[] createCertificateDetailsPage(Certificate certificate, Page certificatePage,
      Page headerPage)
      throws IOException {
    final var certificateHeader = HeaderConverter.header(certificate.getMetadata(), true);
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
        Objects.requireNonNull(content).appendChild(CategoryConverter.category(category)));

    log.debug(doc.html());
    return doc.html();
  }

  private String certificateInfoToHtml(Metadata metadata, Page infoHeaderPage,
      String certificateInfoHtml) throws IOException {

    var doc = createDocFromTemplate(infoPageTemplate, certificateInfoHtml, infoHeaderPage,
        metadata);

    final var content = doc.getElementById(CONTENT);

    assert content != null;
    content.appendChild(ElementProvider.element(Tag.STRONG).text(metadata.getName()));
    content.appendChild(ElementProvider.element(Tag.P).text(metadata.getDescription()));
    content.appendChild(ElementProvider.element(Tag.STRONG).text("Skicka intyg till mottagare"));
    content.appendChild(ElementProvider.element(Tag.P).text(TextFactory.citizenInformation()));
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
    Objects.requireNonNull(title).appendText(TextFactory.title(metadata));
    return doc;
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
}