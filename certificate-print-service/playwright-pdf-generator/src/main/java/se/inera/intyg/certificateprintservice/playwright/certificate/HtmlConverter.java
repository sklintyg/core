package se.inera.intyg.certificateprintservice.playwright.certificate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.playwright.pdf.TemplateToDocumentConverter;

@Slf4j
@Component
@RequiredArgsConstructor
public class HtmlConverter {

  private final TemplateToDocumentConverter templateToDocumentConverter;

//  private String toHtmlString(PrintInformation printInformation,
//      List<Element> children) throws IOException {
//    final var document = templateToDocumentConverter.convert(printInformation);
//    final var content = document.getElementById(CONTENT);
//
//    if (content == null) {
//      throw new IllegalStateException("Could not find element 'content' in document");
//    }
//
//    content.attr(STYLE, "font-family: 'Liberation Sans', sans-serif;");
//    content.appendChildren(children);
//
//    final var script = document.getElementsByTag(Tag.SCRIPT.toString());
//    script.attr("src", templateToDocumentConverter.getScriptSource());
//    return document.html();
//  }

//  public Element convert(Certificate certificate) {
//    final var contentWrapper = element(Tag.DIV)
//        .attr(STYLE, "margin: 0 20mm;");
//    final var metadata = certificate.getMetadata();
//    final var children = new ArrayList<>(certificate.getCategories().stream()
//        .map(CategoryConverter::category)
//        .toList());
//    children.add(getIssuerInformation(metadata));
//    children.add(certificateInformation(metadata));
//    contentWrapper.appendChildren(children);
//    return contentWrapper;
//  }

//  private String convert(PrintInformation printInformation) throws IOException {
//    final var metadata = printInformation.getcertificateMetadata();
//    final var children = new ArrayList<>(printInformation.getCertificate().getCategories().stream()
//        .map(CategoryConverter::category)
//        .toList());
//    children.add(getIssuerInformation(metadata));
//    children.add(certificateInformation(printInformation.getcertificateMetadata()));
//    return toHtmlString(printInformation, children);
//  }

//  private Element certificateInformation(Metadata metadata) {
//    var infoWrapper = new Element(Tag.DIV.toString()).attr("style", "page-break-before:always");
//    infoWrapper.appendChild(certificateName(metadata.getName()));
//    infoWrapper.appendChild(certificateDescription(metadata.getDescription()));
//    infoWrapper.appendChild(element(Tag.BR));
//    infoWrapper.appendChildren(citizenInfo());
//    setLinkColor(infoWrapper);
//    return infoWrapper;
//  }
//
//  private void setLinkColor(Element infoWrapper) {
//    infoWrapper.getAllElements().stream().flatMap(element -> element.children().stream())
//        .filter(element -> element.tagName().equals(Tag.A.toString()))
//        .forEach(element -> element.attr(STYLE, "color: #0000EE;"));
//  }
//
//  private Collection<Node> citizenInfo() {
//    return List.of(
//        element(Tag.STRONG).text("Skicka intyg till mottagare"),
//        element(Tag.P)
//            .attr(STYLE, "white-space: pre-line;")
//            .append(TextFactory.citizenInformation()));
//  }
//
//  private Node certificateDescription(String description) {
//    return element(Tag.P)
//        .attr(STYLE, "white-space: pre-line;")
//        .append(description);
//  }
//
//  private Node certificateName(String name) {
//    return element(Tag.STRONG).text(name);
//  }
//
//  private Element getIssuerInformation(Metadata metadata) {
//    final var issuerInfo = element(Tag.DIV)
//        .attr(STYLE, "break-inside: avoid;");
//
//    if (!metadata.isDraft()) {
//      issuerInfo.appendChild(InformationElementFactory.issuerName(metadata));
//    }
//
//    issuerInfo.appendChild(InformationElementFactory.contactInfo(metadata));
//
//    if (!metadata.isDraft()) {
//      issuerInfo.appendChild(InformationElementFactory.signingDate(metadata));
//    }
//
//    return issuerInfo;
//  }
}
