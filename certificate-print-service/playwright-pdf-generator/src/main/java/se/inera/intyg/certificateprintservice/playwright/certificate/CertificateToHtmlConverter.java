package se.inera.intyg.certificateprintservice.playwright.certificate;

import static se.inera.intyg.certificateprintservice.playwright.Constants.CONTENT;
import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.element.ElementProvider;
import se.inera.intyg.certificateprintservice.playwright.element.InformationElementFactory;
import se.inera.intyg.certificateprintservice.playwright.pdf.TemplateToDocumentConverter;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

@Slf4j
@Component
@RequiredArgsConstructor
public class CertificateToHtmlConverter {

  private final TemplateToDocumentConverter templateToDocumentConverter;

  private String convert(PrintInformation printInformation,
      List<Element> children) throws IOException {
    final var document = templateToDocumentConverter.convert(printInformation);
    final var content = document.getElementById(CONTENT);

    if (content == null) {
      throw new IllegalStateException("Could not find element 'content' in document");
    }

    content.appendChildren(children);

    final var script = document.getElementsByTag(Tag.SCRIPT.toString());
    script.attr("src", templateToDocumentConverter.getScriptSource());
    return document.html();
  }

  public String certificate(PrintInformation printInformation) throws IOException {
    final var metadata = printInformation.getcertificateMetadata();
    final var children = new ArrayList<>(printInformation.getCertificate().getCategories().stream()
        .map(CategoryConverter::category)
        .toList());

    children.add(getSignInfo(metadata));

    children.add(certificateInformation(printInformation.getcertificateMetadata()));

    return convert(printInformation, children);
  }

  private Element certificateInformation(Metadata metadata) {
    var infoWrapper = new Element(Tag.DIV.toString()).attr("style", "page-break-before:always");

    infoWrapper.appendChild(ElementProvider.element(Tag.STRONG).text(metadata.getName()));
    infoWrapper.appendChild(ElementProvider.element(Tag.P)
        .attr(STYLE, "white-space: pre-line;")
        .append(metadata.getDescription()));
    infoWrapper.appendChild(ElementProvider.element(Tag.BR));
    infoWrapper.appendChild(
        ElementProvider.element(Tag.STRONG).text("Skicka intyg till mottagare"));
    infoWrapper.appendChild(ElementProvider.element(Tag.P)
        .attr(STYLE, "white-space: pre-line;")
        .append(TextFactory.citizenInformation()));

    infoWrapper.getAllElements().stream().flatMap(element -> element.children().stream())
        .filter(element -> element.tagName().equals(Tag.A.toString()))
        .forEach(element -> element.attr(STYLE, "color: #0000EE;"));
    return infoWrapper;

  }

  private Element getSignInfo(Metadata metadata) {
    final var signInfo = ElementProvider.element(Tag.DIV)
        .attr(STYLE, "break-inside: avoid;");

    if (!metadata.isDraft()) {
      signInfo.appendChild(InformationElementFactory.issuerName(metadata));
    }

    signInfo.appendChild(InformationElementFactory.contactInfo(metadata));

    if (!metadata.isDraft()) {
      signInfo.appendChild(InformationElementFactory.signingDate(metadata));
    }

    return signInfo;
  }
}
