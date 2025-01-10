package se.inera.intyg.certificateprintservice.playwright.certificate;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.PdfOptions;
import java.io.IOException;
import java.io.InputStream;
import lombok.Data;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;

@Data
public class PrintInformation {

  private String headerHtml;
  private String footerHtml;
  private Certificate certificate;
  private InputStream template;
  private Page headerPage;
  private Page certificateDetailsPage;

  public PdfOptions getPdfOptions() {
    return new PdfOptions()
        .setFormat("A4")
        .setPrintBackground(true)
        .setDisplayHeaderFooter(true)
        .setTagged(true)
        .setHeaderTemplate(this.headerHtml)
        .setFooterTemplate(this.footerHtml);
  }

  public Metadata getcertificateMetadata() {
    return this.certificate.getMetadata();
  }

  public byte[] createPdf(HtmlConverter converter) throws IOException {
    var content = converter.convert(this);
    this.certificateDetailsPage.setContent(content);
    return this.certificateDetailsPage.pdf(this.getPdfOptions());
  }
}
