package se.inera.intyg.certificateprintservice.playwright.certificate;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.PdfOptions;
import java.io.InputStream;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;

@Data
public class PrintInformation {

  private String headerHtml;
  private String footerHtml;
  @Setter(AccessLevel.NONE)
  private PdfOptions pdfOptions;
  private Certificate certificate;
  private InputStream template;
  private Page headerPage;

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

}
