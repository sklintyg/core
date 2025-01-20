package se.inera.intyg.certificateprintservice.playwright;

import com.microsoft.playwright.Page.PdfOptions;
import java.io.IOException;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateprintservice.pdfgenerator.PrintCertificateGenerator;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.playwright.browserpool.BrowserPool;
import se.inera.intyg.certificateprintservice.playwright.browserpool.PlaywrightBrowser;
import se.inera.intyg.certificateprintservice.playwright.converters.ContentConverter;
import se.inera.intyg.certificateprintservice.playwright.converters.FooterConverter;
import se.inera.intyg.certificateprintservice.playwright.converters.HeaderConverter;
import se.inera.intyg.certificateprintservice.playwright.converters.LeftMarginInfoConverter;
import se.inera.intyg.certificateprintservice.playwright.converters.RightMarginInfoConverter;
import se.inera.intyg.certificateprintservice.playwright.document.Document;
import se.inera.intyg.certificateprintservice.playwright.document.Watermark;

@Service
@Slf4j
@RequiredArgsConstructor
public class CertificatePrintGenerator implements PrintCertificateGenerator, InitializingBean {

  @Value("classpath:templates/certificateTemplate.html")
  private Resource template;
  @Value("classpath:templates/tailwindCSS.js")
  private Resource tailwindScript;

  private final BrowserPool browserPool;
  private final ContentConverter contentConverter;
  private final FooterConverter footerConverter;
  private final HeaderConverter headerConverter;
  private final LeftMarginInfoConverter leftMarginInfoConverter;
  private final RightMarginInfoConverter rightMarginInfoConverter;

  private String tailwindCSS;

  private static final PdfOptions PDF_OPTIONS =
      new PdfOptions().setFormat("A4").setTagged(true);

  @Override
  public void afterPropertiesSet() throws Exception {
    tailwindCSS = new String(Base64.getEncoder().encode(tailwindScript.getContentAsByteArray()));
  }

  @Override
  public byte[] generate(final Certificate certificate) {
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

  private byte[] createPdf(PlaywrightBrowser playwrightBrowser, Certificate certificate)
      throws IOException {
    try (
        final var context = playwrightBrowser.getBrowserContext();
        final var page = context.newPage();
    ) {
      final var metadata = certificate.getMetadata();
      final var document = Document.builder()
          .header(headerConverter.convert(metadata))
          .footer(footerConverter.convert(metadata))
          .content(contentConverter.convert(certificate))
          .leftMarginInfo(leftMarginInfoConverter.convert(metadata))
          .rightMarginInfo(rightMarginInfoConverter.convert(metadata))
          .watermark(Watermark.builder().build())
          .certificateName(metadata.getName())
          .certificateType(metadata.getTypeId())
          .certificateVersion(metadata.getVersion())
          .tailWindScript(tailwindCSS)
          .isDraft(metadata.isDraft())
          .build();

      page.setContent(document.build(template, page).html());
      return page.pdf(PDF_OPTIONS);
    }
  }

}