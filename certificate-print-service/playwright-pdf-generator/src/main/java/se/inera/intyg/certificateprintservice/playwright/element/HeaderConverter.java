package se.inera.intyg.certificateprintservice.playwright.element;

import static se.inera.intyg.certificateprintservice.playwright.Constants.HEADER_STYLE;
import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;
import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import java.time.Duration;
import java.time.Instant;
import javax.swing.text.html.HTML.Tag;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

@Slf4j
public class HeaderConverter {

  private HeaderConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static String createHeader(Metadata metadata) {
    final var start1 = Instant.now();
    final var baseWrapper = element(Tag.DIV);

    if (metadata.isSigned()) {
      baseWrapper.appendChild(InformationElementFactory.rightMargin(
          TextFactory.certificateId(metadata.getCertificateId())));
    }

    baseWrapper.appendChild(buildHeaderElement(metadata));
    baseWrapper.appendChild(
        InformationElementFactory.leftMargin(TextFactory.margin(metadata)));

    if (metadata.isDraft()) {
      baseWrapper.appendChild(InformationElementFactory.watermark(TextFactory.draft()));
    }

    final var html = baseWrapper.html();
    log.info("Header creation time: {}", Duration.between(start1, Instant.now()).toMillis());
    return html;
  }

  private static Element buildHeaderElement(Metadata metadata) {
    final var headerElement = headerWrapper();
    final var certificateHeader = certificateHeader(metadata);
    final var pageHeader = pageHeader(metadata.getRecipientLogo());

    pageHeader.appendChild(InformationElementFactory.personId(metadata.getPersonId()));
    certificateHeader.appendChild(
        InformationElementFactory.alert(TextFactory.alert(metadata)));

    headerElement.appendChild(pageHeader);
    headerElement.appendChild(certificateHeader);

    return headerElement;
  }

  private static Element headerWrapper() {
    return element(Tag.DIV)
        .attr("title", "headerElement")
        .attr(STYLE, HEADER_STYLE);
  }

  private static Element certificateHeader(Metadata metadata) {
    final var certificateHeader = new Element(Tag.DIV.toString())
        .attr(STYLE, "margin-bottom: 5mm;");
    certificateHeader.appendChild(InformationElementFactory.title(metadata));
    return certificateHeader;
  }

  private static Element pageHeader(byte[] logo) {

    final var pageHeader = new Element(Tag.DIV.toString()).attr(STYLE, """
          margin: 0 0 10mm 0;
          display: flex;
        """);

    pageHeader.appendChild(ElementProvider.logo(logo));

    return pageHeader;
  }
}
