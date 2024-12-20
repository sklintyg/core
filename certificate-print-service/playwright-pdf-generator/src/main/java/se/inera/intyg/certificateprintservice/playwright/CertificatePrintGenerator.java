package se.inera.intyg.certificateprintservice.playwright;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.PdfOptions;
import com.microsoft.playwright.Playwright;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateprintservice.pdfgenerator.PrintCertificateGenerator;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.certificate.CertificateToHtmlConverter;
import se.inera.intyg.certificateprintservice.playwright.element.HeaderConverter;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

@Service
@Slf4j
@RequiredArgsConstructor
public class CertificatePrintGenerator implements PrintCertificateGenerator {

  @Value("classpath:templates/certificateTemplate.html")
  private Resource template;
  @Value("classpath:templates/infoPageTemplate.html")
  private Resource infoPageTemplate;
  @Value("classpath:templates/tailwind_3.4.16.js")
  private Resource cssScript;
  private final Playwright playwright;

  private final CertificateToHtmlConverter certificateToHtmlConverter;

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
    final var certificateInfo = certificateToHtmlConverter.certificateInformation(infoPageTemplate,
        infoHeaderPage, certificateInfoHeader, metadata);
    final var certificateInfoPdfOptions = getPdfOptions(metadata, certificateInfoHeader);
    certificateInfoPage.setContent(certificateInfo);
    return certificateInfoPage.pdf(certificateInfoPdfOptions);
  }

  private byte[] createCertificateDetailsPage(Certificate certificate, Page certificatePage,
      Page headerPage)
      throws IOException {
    final var certificateHeader = HeaderConverter.header(certificate.getMetadata(), true);
    final var certificateContent = certificateToHtmlConverter.certificate(template, certificate,
        headerPage, certificateHeader);
    final var pdfOptions = getPdfOptions(certificate.getMetadata(), certificateHeader);
    certificatePage.setContent(certificateContent);
    return certificatePage.pdf(pdfOptions);

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

  private String createFooterTemplate(Metadata certificateMetadata) {
    return """
            <div style="width: 100%%; text-align: left; font-size: 10px; margin-left: 30mm">
                <span>%s</span>
            </div>
        """.formatted(TextFactory.applicationOrigin(certificateMetadata));
  }
}