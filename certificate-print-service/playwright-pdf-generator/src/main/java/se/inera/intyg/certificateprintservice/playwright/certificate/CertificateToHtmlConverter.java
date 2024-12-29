package se.inera.intyg.certificateprintservice.playwright.certificate;

import static se.inera.intyg.certificateprintservice.playwright.Constants.CONTENT;

import com.microsoft.playwright.Page;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.element.ElementProvider;
import se.inera.intyg.certificateprintservice.playwright.pdf.TemplateToDocumentConverter;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

@Slf4j
@Component
@RequiredArgsConstructor
public class CertificateToHtmlConverter {

  private final TemplateToDocumentConverter templateToDocumentConverter;

  private  String convert(Resource template, Metadata metadata, Page page, String html,
      List<Node> children)
      throws IOException {
    var document = templateToDocumentConverter.convert(template, html, page, metadata);

    final var content = document.getElementById(CONTENT);

    if (content == null) {
      throw new IllegalStateException("Could not find content in document");
    }

    children.forEach(content::appendChild);

    final var documentHtml = document.html();
    final var htmlWithScript = templateToDocumentConverter.setDocumentScript(documentHtml);
    log.debug(htmlWithScript);
    return htmlWithScript;
  }

  public  String certificate(Resource template, Certificate certificate, Page headerPage,
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

  public String certificateInformation(Resource template, Page infoHeaderPage,
      String certificateInfoHtml, Metadata metadata) throws IOException {
    final var children = new ArrayList<Element>();
    children.add(ElementProvider.element(Tag.STRONG).text(metadata.getName()));
    children.add(ElementProvider.element(Tag.P)
        .attr(STYLE, "white-space: pre-line;")
        .append(metadata.getDescription()));
    children.add(ElementProvider.element(Tag.BR));
    children.add(ElementProvider.element(Tag.STRONG).text("Skicka intyg till mottagare"));
    children.add(ElementProvider.element(Tag.P)
        .attr(STYLE, "white-space: pre-line;")
        .append(TextFactory.citizenInformation()));

    children.stream().flatMap(element -> element.children().stream())
        .filter(element -> element.tagName().equals(Tag.A.toString()))
        .forEach(element -> element.attr(STYLE, "color: #0000EE;"));

    return convert(
        template,
        metadata,
        infoHeaderPage,
        certificateInfoHtml,
        children
    );
  }
}
