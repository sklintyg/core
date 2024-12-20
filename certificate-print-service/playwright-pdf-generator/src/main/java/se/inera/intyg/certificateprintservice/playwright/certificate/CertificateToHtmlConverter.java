package se.inera.intyg.certificateprintservice.playwright.certificate;

import static se.inera.intyg.certificateprintservice.playwright.Constants.CONTENT;

import com.microsoft.playwright.Page;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;
import org.springframework.core.io.Resource;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.element.ElementProvider;
import se.inera.intyg.certificateprintservice.playwright.pdf.TemplateToDocumentConverter;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

@Slf4j
public class CertificateToHtmlConverter {

  private CertificateToHtmlConverter() {
    throw new IllegalStateException("Utility class");
  }

  private static String convert(Resource template, Metadata metadata, Page page, String html,
      List<Node> children)
      throws IOException {
    var document = TemplateToDocumentConverter.convert(template, html, page, metadata);

    final var content = document.getElementById(CONTENT);

    if (content == null) {
      throw new IllegalStateException("Could not find content in document");
    }

    children.forEach(content::appendChild);

    log.debug(document.html());
    return document.html();
  }

  public static String certificate(Resource template, Certificate certificate, Page headerPage,
      String certificateHtml)
      throws IOException {
    final var children = certificate.getCategories().stream()
        .map(CategoryConverter::category)
        .toList();

    return convert(
        template,
        certificate.getMetadata(),
        headerPage,
        certificateHtml,
        children
    );
  }

  public static String certificateInformation(Resource template, Page infoHeaderPage,
      String certificateInfoHtml, Metadata metadata) throws IOException {
    final var children = new ArrayList<Node>();
    children.add(ElementProvider.element(Tag.STRONG).text(metadata.getName()));
    children.add(ElementProvider.element(Tag.P).text(metadata.getDescription()));
    children.add(ElementProvider.element(Tag.STRONG).text("Skicka intyg till mottagare"));
    children.add(ElementProvider.element(Tag.P).text(TextFactory.citizenInformation()));
    return convert(
        template,
        metadata,
        infoHeaderPage,
        certificateInfoHtml,
        children
    );
  }
}
