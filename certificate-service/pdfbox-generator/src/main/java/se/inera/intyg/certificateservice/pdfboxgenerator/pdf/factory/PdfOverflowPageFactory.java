package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificatePdfContext;

@Component
public class PdfOverflowPageFactory {

  public PDPage create(CertificatePdfContext context) {
    final var document = context.getDocument();
    final var templateSpec = context.getTemplatePdfSpecification();

    final var templatePage = document.getPage(templateSpec.overFlowPageIndex().value());
    final var dictionary = new COSDictionary(templatePage.getCOSObject());
    dictionary.removeItem(COSName.ANNOTS);

    return new PDPage(dictionary);
  }
}

